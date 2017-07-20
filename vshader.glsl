// Points parameters
attribute vec4 vPosition;
attribute vec3 vNormal;
attribute vec2 vTexCoord;
uniform vec4 lightPosition;

// Modeling parameters
uniform vec3 scale_factor;
uniform vec3 translate_value;
uniform vec3 rotate_degree;
uniform mat4 view;
uniform mat4 project;

// Variables link to the fragment shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 hPos;
varying vec3 nPos;
varying vec2 texCoord;

void main()
{    
	texCoord = vTexCoord;
	
	// Scale matrix
	vec3 sf = scale_factor;
    mat4 scale = mat4( sf.x, 0.0,  0.0,  0.0,
				   	   0.0,  sf.y, 0.0,  0.0,
				       0.0,  0.0,  sf.z, 0.0,
				       0.0,  0.0,  0.0,  1.0 );
    
    // Translation matrix
    vec3 tv = translate_value;
    mat4 translate = mat4( 1.0,  0.0,  0.0,  0.0,
				   		   0.0,  1.0,  0.0,  0.0,
				   		   0.0,  0.0,  1.0,  0.0,
				   		   tv.x, tv.y, tv.z, 1.0 );
    
    // Rotation matrix
	vec3 angles = radians(rotate_degree);
	vec3 cn = cos(angles);
	vec3 sn = sin(angles);
	mat4 rotate_y = mat4( cn.y, 0.0,-sn.y, 0.0,
						  0.0, 1.0, 0.0, 0.0,
						  sn.y, 0.0, cn.y, 0.0,
						  0.0, 0.0, 0.0, 1.0 );
						  
	// Model matrix					  
	mat4 model = translate * rotate_y * scale;	
	
	// Model & View matrix
	mat4 modelView = view * model;	
	
	// Make the light parameters
	vec3 pos = (modelView * vPosition).xyz;	  
	
	vec3 lightInEye = normalize(lightPosition.xyz - pos);
    vec3 vertexInEye = normalize(-pos);
    vec3 halfInEye = normalize(lightInEye + vertexInEye);
    vec4 normalInEye = normalize(modelView * vec4(vNormal, 0.0));	
    
    lPos = lightInEye.xyz;
    vPos = vertexInEye.xyz;
    hPos = halfInEye.xyz;
    nPos = normalInEye.xyz;		   							  
			
	// Combine the transformation matrix to the position		
    gl_Position = project * modelView * vPosition;
}
