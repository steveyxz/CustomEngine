#version 400 core
out vec4 FragColor;

struct Material {
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
    float reflectivity;
};

struct PointLight {
    vec3 position;

    //[constant, linear, quadratic]
    vec3 attenuation;

    vec3 color;

};

struct SpotLight {
    vec3 position;
    vec3 direction;
    float cutOff;
    float outerCutOff;

    //[constant, linear, quadratic]
    vec3 attenuation;

    vec3 color;
};

#define NR_LIGHTS 4

in vec3 FragPos;
in vec3 Normal;
in vec2 TexCoords;

uniform vec3 viewPos;
uniform PointLight pointLights[NR_LIGHTS];
uniform SpotLight spotLights[NR_LIGHTS];
uniform Material material;
uniform vec3 ambientLightColor;
uniform float useSpecularLighting;

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir);
vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir);

void main()
{
    vec3 norm = normalize(Normal);
    vec3 viewDir = normalize(viewPos - FragPos);

    vec3 result = vec3(0.0);
    for(int i = 0; i < NR_LIGHTS; i++) {
        PointLight light = pointLights[i];
        if (light.attenuation != vec3(0, 0, 0)) result += CalcPointLight(light, norm, FragPos, viewDir);
    }
    for(int i = 0; i < NR_LIGHTS; i++) {
        SpotLight light2 = spotLights[i];
        if (light2.attenuation != vec3(0, 0, 0)) result += CalcSpotLight(light2, norm, FragPos, viewDir);
    }
    if (result == vec3(0.0)) {
        result = ambientLightColor * vec3(texture(material.diffuse, TexCoords));
    }

    FragColor = vec4(result, 1.0);
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * (distance * distance));
    vec3 ambient = ambientLightColor * vec3(texture(material.diffuse, TexCoords));
    vec3 diffuse = light.color * diff * vec3(texture(material.diffuse, TexCoords));
    vec3 specular = light.color * spec;
    if (useSpecularLighting == 1) {
        specular = specular * vec3(texture(material.specular, TexCoords));
    }
    ambient *= attenuation;
    diffuse *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);
}

vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(normal, lightDir), 0.0);
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    float distance = length(light.position - fragPos);
    float attenuation = 1.0 / (light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * (distance * distance));
    float theta = dot(lightDir, normalize(-light.direction));
    float epsilon = light.cutOff - light.outerCutOff;
    float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);
    vec3 ambient = ambientLightColor * vec3(texture(material.diffuse, TexCoords));
    vec3 diffuse = light.color * diff * vec3(texture(material.diffuse, TexCoords));
    vec3 specular = light.color * spec;
    if (useSpecularLighting == 1) {
        specular = specular * vec3(texture(material.specular, TexCoords));
    }
    ambient *= attenuation * intensity;
    diffuse *= attenuation * intensity;
    specular *= attenuation * intensity;
    return (ambient + diffuse + specular);
}