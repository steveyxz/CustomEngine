#version 400 core
#define MAXLIGHTS 12

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toCameraVector;
in vec3 toLightVector[MAXLIGHTS];
in float visibility;

out vec4 out_Color;

uniform vec3 lightColour[MAXLIGHTS];
uniform vec3 attenuation[MAXLIGHTS];
uniform sampler2D modelTexture;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void) {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i < MAXLIGHTS; i++) {
        float distance = length(toLightVector[i]);
        float attFactor = attenuation[i].x + (attenuation[i].y * distance) + (attenuation[i].z * distance * distance);
        vec3 unitLightVector = normalize(toLightVector[i]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        totalDiffuse = totalDiffuse + (brightness * lightColour[i]) / attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[i]) / attFactor;

    }

    totalDiffuse = max(totalDiffuse, 0.2);

    vec4 textureColour = texture(modelTexture, pass_textureCoords);
    if (textureColour.a < 0.5) {
        discard;
    }

    out_Color = vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular, 1.0);
    out_Color = mix(vec4(skyColour, 1.0), out_Color, visibility);

}
