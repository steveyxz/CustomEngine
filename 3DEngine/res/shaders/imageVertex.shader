#version 400 core
#define MAXLIGHTS 12

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[MAXLIGHTS];
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[MAXLIGHTS];
uniform int lightCount;
//0 if false, 1 if true
uniform float useFakeLighting;

const float density = 0.007;
const float gradient = 1.5;
//const float density = 0;
//const float gradient = 1;



void main(void) {

    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelative = viewMatrix * worldPosition;
    gl_Position = projectionMatrix * positionRelative;
    pass_textureCoords = textureCoords;

    vec3 actualNormal = normal;
    if (useFakeLighting > 0.5) {
        actualNormal = vec3(0.0, 1.0, 0.0);
    }

    surfaceNormal = (transformationMatrix * vec4(actualNormal, 0.0)).xyz;
    for (int i = 0; i < MAXLIGHTS; i++) {
        toLightVector[i] = lightPosition[i] - worldPosition.xyz;
    }
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    float distance = length(positionRelative.xyz);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);

}