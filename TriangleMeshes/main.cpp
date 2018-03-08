/*
 * CSC 305 201801 UVIC
 * The purpose of this source file was to demonstrate the Mesh class which you may use in assignment 2
 * Its previous only functionality is to render vertices/normals/textures and load textures from png files
 * A demonstration of an ImGui menu window was also included in this file
 *
 * This file was updated by Dana Huget, V00860786 for Assignment 2 submission
 * February 26, 2017
*/
#include "Mesh/Mesh.h"
#include "OpenGP/GL/glfw_helpers.h"

#include <OpenGP/types.h>
#include <OpenGP/MLogger.h>
#include <OpenGP/GL/Application.h>
#include <OpenGP/GL/ImguiRenderer.h>
#include <fstream>
#include <iostream>
#include <math.h>

using namespace OpenGP;
using namespace std;


// function to read input .OBJ file
void load_obj(const char* file, vector<Vec3> &vertList, vector<unsigned int> &indexList, vector<Vec3> &normList, vector<Vec2> &tCoordList){
    /// Temporary variables to store contents of .obj file
    //vector<unsigned int> vertIndices, coorIndices, normIndices, temp_index;
    //vector<Vec3> temp_verts;
    vector<Vec2> temp_coors;
    vector<Vec3> temp_norms;
    ifstream inputFile;

    inputFile.open(file, ifstream::in);
    if(!file){
        cerr << "Cannot open " << file << endl;
        exit(1);
    }
    //cout << "inputFile: \n" << endl;

    string line;
    /// parse each line of the file
    while(getline(inputFile, line)){
        //cout << line << endl;
        if (line.substr(0,2) == "v "){
            /// list of geometric vertices with x,y,z coordinates
            //cout << "v :" << line << endl;
            string s;
            Vec3 vertex;
            sscanf(line.c_str(), "%s %f %f %f\n", &s, &vertex(0), &vertex(1), &vertex(2));
            //cout << "vertex: " << vertex(0) << " " << vertex(1) << " " << vertex(2) << endl;
            vertList.push_back(vertex);
        }
        else if (line.substr(0,2) == "vt"){
            /// list of texture coordinates in u,v coordinates
            //cout << "vt:" << line << endl;
            string s;
            Vec2 uv;
            sscanf(line.c_str(), "%s %f %f\n", &s, &uv(0), &uv(1));
            //cout << "uv: " << uv(0) << " " << uv(1) << endl;
            temp_coors.push_back(uv);
        }
        else if (line.substr(0,2) == "vn"){
            /// list of vertex normals in x,y,z form; might not be unit vectors
            //cout << "vn:" << line << endl;
            string s;
            Vec3 normal;
            sscanf(line.c_str(), "%s %f %f %f\n", &s, &normal(0), &normal(1), &normal(2));
            //cout << "normal: " << normal(0) << " " << normal(1) << " " << normal(2) << endl;
            temp_norms.push_back(normal);
        }
        else if (line.substr(0,2) == "f "){
            /// polygonal face element
            //cout << "f :" << line << endl;
            string s;
            unsigned int v1, v2, v3, vt1, vt2, vt3, n1,n2,n3;
            //unsigned int coorIndex[3]; normIndex[3];
            int match = sscanf(line.c_str(), "%s %d %d %d\n", &s, &v1, &v2, &v3);
            if(match == 4 ){
                v1--;v2--;v3--;//vt1--, vt2--, vt3--, n1--, n2--, n3--;
                indexList.push_back(v1); // Face 1
                indexList.push_back(v2);
                indexList.push_back(v3);
            }

        }else if(line.substr(0,1) == "#" ){
            /// ignore comments
        }
    }

    inputFile.close();
}

// function to read input .OBJ file with f in form %d/%d/%d
void load_fobj(const char* file, vector<Vec3> &vertList, vector<unsigned int> &indexList, vector<Vec3> &normList, vector<Vec2> &tCoordList){
    /// Temporary variables to store contents of .obj file
    //vector<unsigned int> vertIndices, coorIndices, normIndices, temp_index;
    //vector<Vec3> temp_verts;
    vector<Vec2> temp_coors;
    vector<Vec3> temp_norms;
    ifstream inputFile;

    inputFile.open(file, ifstream::in);
    if(!file){
        cerr << "Cannot open " << file << endl;
        exit(1);
    }
    //cout << "inputFile: \n" << endl;

    string line;
    /// parse each line of the file
    while(getline(inputFile, line)){
        //cout << line << endl;
        if (line.substr(0,2) == "v "){
            /// list of geometric vertices with x,y,z coordinates
            //cout << "v :" << line << endl;
            string s;
            Vec3 vertex;
            sscanf(line.c_str(), "%s %f %f %f\n", &s, &vertex(0), &vertex(1), &vertex(2));
            //cout << "vertex: " << vertex(0) << " " << vertex(1) << " " << vertex(2) << endl;
            vertList.push_back(vertex);
        }
        else if (line.substr(0,2) == "vt"){
            /// list of texture coordinates in u,v coordinates
            //cout << "vt:" << line << endl;
            string s;
            Vec2 uv;
            sscanf(line.c_str(), "%s %f %f\n", &s, &uv(0), &uv(1));
            //cout << "uv: " << uv(0) << " " << uv(1) << endl;
            temp_coors.push_back(uv);
        }
        else if (line.substr(0,2) == "vn"){
            /// list of vertex normals in x,y,z form; might not be unit vectors
            //cout << "vn:" << line << endl;
            string s;
            Vec3 normal;
            sscanf(line.c_str(), "%s %f %f %f\n", &s, &normal(0), &normal(1), &normal(2));
            //cout << "normal: " << normal(0) << " " << normal(1) << " " << normal(2) << endl;
            temp_norms.push_back(normal);
        }
        else if (line.substr(0,2) == "f "){
            /// polygonal face element
            //cout << "f :" << line << endl;
            string s;
            unsigned int v1, v2, v3, vt1, vt2, vt3, n1,n2,n3;
            //unsigned int coorIndex[3]; normIndex[3];
            int match = sscanf(line.c_str(), "%s %d/%d/%d %d/%d/%d %d/%d/%d\n", &s, &v1, &vt1, &n1, &v2, &vt2, &n2, &v3, &vt3, &n3);
            if(match == 10){
                v1--;v2--;v3--, vt1--, vt2--, vt3--, n1--, n2--, n3--;
                indexList.push_back(v1); // Face 1
                indexList.push_back(v2);
                indexList.push_back(v3);
                tCoordList.push_back(temp_coors.at(vt1));
                tCoordList.push_back(temp_coors.at(vt2));
                tCoordList.push_back(temp_coors.at(vt3));
                normList.push_back(temp_norms.at(n1));
                normList.push_back(temp_norms.at(n2));
                normList.push_back(temp_norms.at(n3));
            }

        }else if(line.substr(0,1) == "#" ){
            /// ignore comments
        }
    }

    inputFile.close();
}

/// function to generate cube triangle mesh
/// the cube extends from -1 to 1 in each of the x, y, and z directions
void cubeObj(){

    ofstream f("cube.obj");

    if(f.is_open()){

        f << "#cube.obj\n\n";

        /// coordinates of the 8 vertices in 3D space
        f << "v -1.0 1.0 1.0" << endl;
        f << "v -1.0 -1.0 1.0" << endl;
        f << "v 1.0 1.0 1.0" << endl;
        f << "v 1.0 -1.0 1.0" << endl;
        f << "v 1.0 1.0 -1.0" << endl;
        f << "v 1.0 -1.0 -1.0" << endl;
        f << "v -1.0 1.0 -1.0" << endl;
        f << "v -1.0 -1.0 -1.0\n" << endl;

        /// texture coordinates of the vertices
        f << "vt 0.0 0.0" << endl;
        f << "vt 1.0 0.0" << endl;
        f << "vt 0.0 1.0" << endl;
        f << "vt 1.0 1.0\n" << endl;

        /// normals of hte vertices
        f << "vn 0.0 0.0 1.0" << endl;
        f << "vn 0.0 0.0 -1.0" << endl;
        f << "vn 0.0 1.0 0.0" << endl;
        f << "vn 0.0 -1.0 0.0" << endl;
        f << "vn 1.0 0.0 0.0" << endl;
        f << "vn -1.0 0.0 0.0\n" << endl;

        /// the position, texture coordinate, and normal indices of the 12 triangles that make up the mesh
        f << "f 1/3/1 2/1/1 3/4/1" << endl;
        f << "f 3/4/1 2/1/1 4/2/1" << endl;
        f << "f 3/3/5 4/1/5 5/4/5" << endl;
        f << "f 5/4/5 4/1/5 6/2/5" << endl;
        f << "f 5/3/2 6/1/2 7/4/2" << endl;
        f << "f 7/4/2 6/1/2 8/2/2" << endl;
        f << "f 7/3/6 8/1/6 1/4/6" << endl;
        f << "f 1/4/6 8/1/6 2/2/6" << endl;
        f << "f 7/3/3 1/1/3 5/4/3" << endl;
        f << "f 5/4/3 1/1/3 3/2/3" << endl;
        f << "f 2/1/4 8/2/4 4/3/4" << endl;
        f << "f 4/3/4 8/2/4 6/4/4" << endl;

        f.close();
    } else{
        cerr << "Cannot open output file" << endl;
        exit(1);
    }
}

/// function to generate vertices in a circle triangle mesh
void circle(Vec3 center, unsigned int r, vector<Vec3> &circleVertList){
    int n = 32; // number of triangle pieces to divide circle

    // add center vertex
    circleVertList.push_back(center);

    for(int i = 0; i <= n; i++){
        Vec3 vertex;
        // circle lies in x-z plane
        vertex(0) = center(0) + (r * cos(i * 2*M_PI / n)); // x position
        vertex(1) = center(1); // y position does not change
        vertex(2) = center(2) + (r * sin(i * 2*M_PI / n)); // z position
        // add vertices to list
        circleVertList.push_back(vertex);
    }

}

/// function to generate faces of triangles on the two circle caps of a cylinder ie indexList
void cylinderCap(vector<Vec3> circleVertList, vector<unsigned int> &indexList){
    // n number of triangles on circle i.e number of vertices on circumfrence
    int n = circleVertList.size()/2 - 2;
    //cout << "n = " << circleVertList.size() << endl;
    // m index of starting vertex
    int m = 2;
    int c = m;

    // for each top triangle
    for(int i = 0; i < n; i++){
        // Face 1
        indexList.push_back(m);
        indexList.push_back(1); // center vertex
        indexList.push_back(m+1);
        m++;
        c = m;
    }
    //cout << "m = " << m << endl;

    // next center point
    c++;
    m+=2;

    // for each bottom triangle
    for(int i = 0; i < n; i++){
        // Face 1
        indexList.push_back(m+1);
        indexList.push_back(c); // center vertex
        indexList.push_back(m);
        m++;
    }

}

/// function to generate faces of triangles on the shell of a cylinder (ie indexList) after the circle faces have been added
void cylinderShell(vector<Vec3> circleVertList, vector<unsigned int> &indexList){
    // 68 vertices in cylinder
    cout << "n = " << circleVertList.size() << endl;
    int n = circleVertList.size()/2; // 68 vertices in cylinder
    int m = circleVertList.size()/2 + 2; // 36

    for(int i = 0; i < n -2; i++){
        indexList.push_back(m); // Face 1
        indexList.push_back(i+2);
        indexList.push_back(m+1);
        indexList.push_back(i+2);// Face 2
        indexList.push_back(i+3);
        indexList.push_back(m+1);
        m++;
    }

}

/// function to generate cylinder triangle mesh
/// the cylinder has radius 1 and height 2, centered at origin, tessellated with n divisions arranged radially
/// around the outer surface
void cylinderObj(){

    ofstream f("cylinder.obj");

    if(f.is_open()){

        f << "#cylinder.obj\n\n";

        /// coordinates of the vertices in 3D space
        vector<Vec3> circleVertList;
        unsigned int r = 1;
        // top circle
        Vec3 center(0, 1, 0);
        circle(center, r, circleVertList);

        // bottom circle
        Vec3 center2(0, -1, 0);
        circle(center2, r, circleVertList);

        for (Vec3 n : circleVertList) {
            f << "v " << n(0) << " " << n(1) << " " << n(2) << endl;
        }

        /// texture coordinates of the vertices
        //f << "vt " << endl;

        /// normals of hte vertices
        //f << "vn " << endl;

        /// the position, texture coordinate, and normal indices of the triangles that make up the mesh
        vector<unsigned int> indexList;
        // creates top and bottom faces i.e. triangle indices
        cylinderCap(circleVertList, indexList);
        cout << "cylinder index " << indexList.size() << endl;
        cylinderShell(circleVertList, indexList);

        int j = 0;
        for (int i = 0; i < indexList.size(); i+=3){
            f << "f " << indexList.at(j) << " " << indexList.at(j+1) << " " << indexList.at(j+2) << endl;
            j+=3;
        }

        f.close();
    } else{
        cerr << "Cannot open output file" << endl;
        exit(1);
    }
}

/// function to generate a torus triangle mesh
void torusObj(unsigned int n, unsigned int m, float r1){
    // r is major radius and r1 is minor radius

    //cout << "r1 minor = " << r1 << endl;
    float r = 1.0f; // major radius, distance from origin
    //r1 = r - r1;
    //cout << "r1 minor = " << r1 << endl;
    vector<Vec3> torusVertList;

    float thetaIncrease = (M_PI*2.0f)/(float)n; // angles go from 0 to 2pi
    float phiIncrease = (M_PI*2.0f)/(float)m;

    // generate vertices
    float t = 0.0f;
    float p = 0.0f;

    for(int i = 0; i < n; i++){
        //theta loop
        p = 0.0f; // reset phi after finishing phi loops
        for(int j = 0; j < m; j++){
            //phi loop

            Vec3 point; // P = (x,y,z)
            point(0) = cos(t)*(r + r1*cos(p)); //x
            point(2) = sin(t)*(r + r1*cos(p)); //y
            point(1) = r1*sin(p); //z

            //cout << "torus point " << point(0) << " " << point(1) << " " << point(2) << endl;

            torusVertList.push_back(point);

            p += phiIncrease;
        }
        t += thetaIncrease;
    }

    // generate faces
    vector<unsigned int> indexList;
    // faces are 1-based whereas index is 0-based
    int w = m+1;
    int u = 1;
    int v = 1;

    for (int i = 0; i < n; i++){
        for(int j = 1; j < m; j++){ // faces are 1-based whereas index is 0-based
            indexList.push_back(u); // Face 1
            indexList.push_back(w+1);
            indexList.push_back(w);
            indexList.push_back(u);// Face 2
            indexList.push_back(u+1);
            indexList.push_back(w+1);
            w++;u++;
            //cout << "a w = " << w << "x = " << x <<  "u = " << u << "v = " << v << endl;
        }
        indexList.push_back(u); // Face 1
        indexList.push_back(u+1);
        indexList.push_back(w);
        indexList.push_back(u);// Face 2
        indexList.push_back(v);
        indexList.push_back(u+1);
        ///cout << "b w = " << w << "x = " << x <<  "u = " << u << "v = " << v << endl;
        w++; u++; v+=m;
        //cout << "w = " << w << "x = " << x <<  "u = " << u << "v = " << v << endl;
    }
    indexList.pop_back();indexList.pop_back();indexList.pop_back();

    int x = torusVertList.size()-m+1;

    for(int i = 1; i < m; i++){ // faces are 1-based whereas index is 0-based
        indexList.push_back(x); // Face 1
        indexList.push_back(i+1);
        indexList.push_back(i);
        indexList.push_back(x);// Face 2
        indexList.push_back(x+1);
        indexList.push_back(i+1);
        x++;
    }
    cout << "w = " << w << "x = " << x <<  "u = " << u << "v = " << v << endl;
    indexList.push_back(x); // Face 1
    indexList.push_back(1);
    indexList.push_back(m);
    indexList.push_back(x);// Face 2
    indexList.push_back(x-(m-1));
    indexList.push_back(1);
    //w++; u++; v+=m;



    ofstream f("torus.obj");

    if(f.is_open()){

        f << "#torus.obj\n\n";

        /// coordinates of the 8 vertices in 3D space
        //f << "v " << endl;
        for (Vec3 n : torusVertList) {
            f << "v " << n(0) << " " << n(1) << " " << n(2) << endl;
        }

        /// texture coordinates of the vertices
        //f << "vt " << endl;

        /// normals of hte vertices
        //f << "vn " << endl;

        /// the position, texture coordinate, and normal indices of the 12 triangles that make up the mesh
        int j = 0;
        for (int i = 0; i < indexList.size(); i+=3){
            f << "f " << indexList.at(j) << " " << indexList.at(j+1) << " " << indexList.at(j+2) << endl;
            j+=3;
        }
        f.close();

    } else{
        cerr << "Cannot open output file" << endl;
        exit(1);
    }

}

/// function to generate a sphere triangle mesh and output to obj file
/// the sphere has radius 1 and is centered at the origin
/// it is tessellated in lat-long fashion with n divisions around equator and m divisions from pole to pole
/// m divisions requires m+1 vertices from pole to pole
/// North pole is at (0,1,0), South pole is at (0,-1,0), points on Greenwich meridian have coordinates (0,y,z) with z>0
void sphereObj(unsigned int n, unsigned int m){

    unsigned int r = 1.0; // radius
    vector<Vec3> sphereVertList;

    float thetaIncrease = (M_PI)/(float)m; // angle goes from 0 to pi
    float phiIncrease = (M_PI*2.0f)/(float)n; // angle goes from 0 to 2pi

    // generate vertices
    float t = 0.0f;
    float p = 0.0f;
    for(int i =0; i < m; i++){
        //theta loop
        p = 0.0f;
        for(int j = 0; j < n; j++){
            //phi loop

            Vec3 point; // P = (x,y,z)
            point(0) = r * cos(p)*sin(t); //x
            point(1) = r * cos(t); //y
            point(2) = r * sin(p)*sin(t); //z

            //cout << "point " << point(0) << " " << point(1) << " " << point(2) << endl;

            sphereVertList.push_back(point);

            p += phiIncrease;
        }
        t += thetaIncrease;
    }
    //add south pole
    sphereVertList.push_back(Vec3(0,-1,0));

    // generate faces
    // n number of triangles on circle i.e number of vertices on circumfrence
    int num = sphereVertList.size()/2 - 2;
    cout << "sphere num vertices = " << sphereVertList.size() << endl;
    // x index of starting vertex
    int x = n+1; // faces are 1-based whereas index is 0-based
    vector<unsigned int> indexList;

    // add top of sphere triangle faces
    for(int i = 0; i < n-1; i++){
        // Face 1
        indexList.push_back(x);
        indexList.push_back(1); // center vertex
        indexList.push_back(x+1);
        x++;
    }

    indexList.push_back(x);
    indexList.push_back(1);
    indexList.push_back(n+1);
    x++;
    //cout << "x = " << x << endl;

    int w = n+1;
    //cout << "w = " << w << endl;

    indexList.push_back(x);
    indexList.push_back(x-1);
    indexList.push_back(w);

    // add each band across sphere triangle faces
    for(int i = 0; i < n; i++){
        for(int j = 0; j < m-2; j++){
            indexList.push_back(x); // Face 1
            indexList.push_back(w);
            indexList.push_back(w+1);
            indexList.push_back(x);// Face 2
            indexList.push_back(w+1);
            indexList.push_back(x+1);
            x++; w++;
        }
    }
    //cout << "x bottom = " << x << endl;
    //cout << "w bottom = " << w << endl;
    // add bottom of sphere triangle faces
    for(int i = 0; i < n-1; i++){
        indexList.push_back(x);
        indexList.push_back(w);
        indexList.push_back(w+1);
        w++;
    }


    ofstream f("sphere.obj");

    if(f.is_open()){

        f << "#sphere.obj\n\n";

        /// coordinates of the 8 vertices in 3D space
        //f << "v " << endl;
        for (Vec3 n : sphereVertList) {
            f << "v " << n(0) << " " << n(1) << " " << n(2) << endl;
        }

        /// texture coordinates of the vertices
        //f << "vt " << endl;

        /// normals of hte vertices
        //f << "vn " << endl;

        /// the position, texture coordinate, and normal indices of the 12 triangles that make up the mesh
        int j = 0;
        for (int i = 0; i < indexList.size(); i+=3){
            f << "f " << indexList.at(j) << " " << indexList.at(j+1) << " " << indexList.at(j+2) << endl;
            j+=3;
        }

        f.close();
    } else{
        cerr << "Cannot open output file" << endl;
        exit(1);
    }
}

int main() {

    Application app;
    ImguiRenderer imrenderer;
    Mesh renderMesh;

    /// Example rendering a mesh
    /// Call to compile shaders
    renderMesh.init();

    /// Call to create triangle meshes (output to obj file)
    cubeObj();
    sphereObj(32, 16);
    cylinderObj();
    torusObj(32, 16, 0.25f);

    /// Load Vertices and Indices (minimum required for Mesh::draw to work)
    vector<Vec3> vertList;
    vector<unsigned int> indexList;
   /* renderMesh.loadVertices(vertList, indexList);*/

    /// Load normals
    vector<Vec3> normList;
    /*renderMesh.loadNormals(normList);*/

    /// Load textures (assumes texcoords)
    //renderMesh.loadTextures("earth.png");

    /// Load texture coordinates (assumes textures)
    vector<Vec2> tCoordList;
    /*renderMesh.loadTexCoords(tCoordList);*/

    load_fobj("cube.obj", vertList, indexList, normList, tCoordList);
    renderMesh.loadVertices(vertList, indexList);
    renderMesh.loadNormals(normList);
    renderMesh.loadTextures("1.png");
    renderMesh.loadTexCoords(tCoordList);


    Mesh renderMeshCyl;
    renderMeshCyl.init();
    vertList.clear(); indexList.clear(); normList.clear(); tCoordList.clear();
    load_obj("cylinder.obj", vertList, indexList, normList, tCoordList);
    renderMeshCyl.loadVertices(vertList, indexList);
    renderMeshCyl.loadNormals(normList);
    renderMeshCyl.loadTextures("1.png");
    renderMeshCyl.loadTexCoords(tCoordList);

    Mesh renderMeshSph;
    renderMeshSph.init();
    vertList.clear(); indexList.clear(); normList.clear(); tCoordList.clear();
    load_obj("sphere.obj", vertList, indexList, normList, tCoordList);
    renderMeshSph.loadVertices(vertList, indexList);
    renderMeshSph.loadNormals(normList);
    renderMeshSph.loadTextures("1.png");
    renderMeshSph.loadTexCoords(tCoordList);

    Mesh renderMeshTor;
    renderMeshTor.init();
    vertList.clear(); indexList.clear(); normList.clear(); tCoordList.clear();
    load_obj("torus.obj", vertList, indexList, normList, tCoordList);
    renderMeshTor.loadVertices(vertList, indexList);
    renderMeshTor.loadNormals(normList);
    renderMeshTor.loadTextures("1.png");
    renderMeshTor.loadTexCoords(tCoordList);

    /// Create main window, set callback function
    auto &window1 = app.create_window([&](Window &window){
        int width, height;
        tie(width, height) = window.get_size();

        glEnable(GL_DEPTH_TEST);
        glDepthMask(GL_TRUE);
        glClearColor(0.0f, 0.0f, 0.0f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        /// Wireframe rendering, might be helpful when debugging your mesh generation
        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        float ratio = width / (float) height;
        Mat4x4 modelTransform = Mat4x4::Identity();
        Mat4x4 model = modelTransform.matrix();
        Mat4x4 projection = OpenGP::perspective(70.0f, ratio, 0.1f, 10.0f);

        //camera movement
        float time = .5f * (float)glfwGetTime();
        Vec3 cam_pos(2*cos(time), 2.0, 2*sin(time));
        Vec3 cam_look(0.0f, 0.0f, 0.0f);
        Vec3 cam_up(0.0f, 1.0f, 0.0f);
        Mat4x4 view = OpenGP::lookAt(cam_pos, cam_look, cam_up);

        renderMeshCyl.draw(model, view, projection);
    });
    window1.set_title("Cylinder Mesh");

    /// Create main window, set callback function
    auto &window2 = app.create_window([&](Window &window){
        int width, height;
        tie(width, height) = window.get_size();

        glEnable(GL_DEPTH_TEST);
        glDepthMask(GL_TRUE);
        glClearColor(0.0f, 0.0f, 0.0f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        /// Wireframe rendering, might be helpful when debugging your mesh generation
        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        float ratio = width / (float) height;
        Mat4x4 modelTransform = Mat4x4::Identity();
        Mat4x4 model = modelTransform.matrix();
        Mat4x4 projection = OpenGP::perspective(70.0f, ratio, 0.1f, 10.0f);

        //camera movement
        float time = .5f * (float)glfwGetTime();
        Vec3 cam_pos(2*cos(time), 2.0, 2*sin(time));
        Vec3 cam_look(0.0f, 0.0f, 0.0f);
        Vec3 cam_up(0.0f, 1.0f, 0.0f);
        Mat4x4 view = OpenGP::lookAt(cam_pos, cam_look, cam_up);

        renderMesh.draw(model, view, projection);
    });
    window2.set_title("Cube Mesh");

    /// Create main window, set callback function
    auto &window3 = app.create_window([&](Window &window){
        int width, height;
        tie(width, height) = window.get_size();

        glEnable(GL_DEPTH_TEST);
        glDepthMask(GL_TRUE);
        glClearColor(0.0f, 0.0f, 0.0f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        /// Wireframe rendering, might be helpful when debugging your mesh generation
        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        float ratio = width / (float) height;
        Mat4x4 modelTransform = Mat4x4::Identity();
        Mat4x4 model = modelTransform.matrix();
        Mat4x4 projection = OpenGP::perspective(70.0f, ratio, 0.1f, 10.0f);

        //camera movement
        float time = .5f * (float)glfwGetTime();
        Vec3 cam_pos(2*cos(time), 2.0, 2*sin(time));
        Vec3 cam_look(0.0f, 0.0f, 0.0f);
        Vec3 cam_up(0.0f, 1.0f, 0.0f);
        Mat4x4 view = OpenGP::lookAt(cam_pos, cam_look, cam_up);

        renderMeshSph.draw(model, view, projection);
    });
    window3.set_title("Sphere Mesh");

    /// Create main window, set callback function
        auto &window4 = app.create_window([&](Window &window){
            int width, height;
            tie(width, height) = window.get_size();

            glEnable(GL_DEPTH_TEST);
            glDepthMask(GL_TRUE);
            glClearColor(0.0f, 0.0f, 0.0f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            /// Wireframe rendering, might be helpful when debugging your mesh generation
            glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

            float ratio = width / (float) height;
            Mat4x4 modelTransform = Mat4x4::Identity();
            Mat4x4 model = modelTransform.matrix();
            Mat4x4 projection = OpenGP::perspective(70.0f, ratio, 0.1f, 10.0f);

            //camera movement
            float time = .5f * (float)glfwGetTime();
            Vec3 cam_pos(2*cos(time), 2.0, 2*sin(time));
            Vec3 cam_look(0.0f, 0.0f, 0.0f);
            Vec3 cam_up(0.0f, 1.0f, 0.0f);
            Mat4x4 view = OpenGP::lookAt(cam_pos, cam_look, cam_up);

            renderMeshTor.draw(model, view, projection);
        });
        window4.set_title("Torus Mesh");

    /// Create window for IMGUI, set callback function
    /*auto &window4 = app.create_window([&](Window &window){
        int width, height;
        tie(width, height) = window.get_size();

        imrenderer.begin_frame(width, height);

        ImGui::BeginMainMenuBar();
        ImGui::MenuItem("File");
        ImGui::MenuItem("Edit");
        ImGui::MenuItem("View");
        ImGui::MenuItem("Help");
        ImGui::EndMainMenuBar();

        ImGui::Begin("Test Window 1");
        ImGui::Text("This is a test imgui window");
        ImGui::End();

        glClearColor(0.15f, 0.15f, 0.15f, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        imrenderer.end_frame();
    });
    window4.set_title("imgui Test");*/

    return app.run();
}
