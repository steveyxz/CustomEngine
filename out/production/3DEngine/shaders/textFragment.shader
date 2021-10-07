#version 330

in vec2 pass_textureCoords;

out vec4 out_colour;

uniform vec3 colour;
uniform sampler2D fontAtlas;

const float width = 0.5;
const float edge = 0.1;

void main(void){

    vec4 texture = texture(fontAtlas, pass_textureCoords);
    float distance = 1.0 - texture.a;
    float alpha = 1.0 - smoothstep(width, width + edge, distance);


    out_colour = vec4(colour, alpha);

}