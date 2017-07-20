// Texture parameters
varying vec2 texCoord;
uniform sampler2D texture;

// Light parameters
uniform vec4 lightColor;
uniform vec4 diffuseColor;
uniform vec4 specularColor;
uniform float specularExponent;

// Check if is the SUN
uniform float ifsun;

// Variables from the vertex shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 hPos;
varying vec3 nPos;

void main() 
{ 
	vec4 color;

	// If it is not the SUN, there are diffuse and specular color
    if (ifsun == 0.0){

	// Diffuse color setup
	float Kd = max(dot(lPos, nPos), 0.0);
    vec4 diffuse = lightColor * diffuseColor * Kd;

	// Specular color setup
	float Ks = pow(max(dot(nPos, hPos), 0.0), specularExponent);
	vec4 specular = lightColor * specularColor * Ks;
	
	if (dot(lPos, nPos) < 0.0)
		specular = vec4(0.0, 0.0, 0.0, 1.0);
		
	// Combine color
	color = diffuse + specular;
  	}
  	
  	// If it is the SUN, there is only ambient color
  	else {
  	color = vec4(1.0, 1.0, 1.0, 1.0);
  	}
  	
  	// Final combine the color and texture
  	gl_FragColor = color * texture(texture, texCoord);
} 
