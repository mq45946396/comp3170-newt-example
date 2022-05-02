#version 400

in vec3 a_position;              // model space position

uniform mat4 u_projectionMatrix; // view -> ndc
uniform mat4 u_modelMatrix;      // model -> world

void main() {
	gl_Position = u_projectionMatrix * u_modelMatrix * vec4(a_position, 1.0);
}