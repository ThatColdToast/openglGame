#version 330

uniform sampler2D sampleTexture;

out vec4 fragColor;

in vec2 texture;
in vec4 gl_FragCoord;

void main() {
//     vec4 unshadedColor = texture2D(sampleTexture, texture);
//
//     float brightness = (texture.x * texture.y) * ((1 - texture.x) * (1 - texture.y));
//     brightness += 0.8;
//     pow(brightness, 2);
//
//     fragColor = unshadedColor * brightness;

//     vec4 unshadedColor = texture2D(sampleTexture, texture);



//     fragColor = unshadedColor;

	fragColor = texture2D(sampleTexture, texture); //vec4(1,1,0,1);
}

