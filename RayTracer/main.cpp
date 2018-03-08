/** main.cpp within icg/raytrace
 * Skeleton code provided by Author Firmino for CSC 305 Spring 2018
 * Edited by Dana Huget (V00860786) Feb 8, 2018 for Assignment 1
 */

#include "OpenGP/Image/Image.h"
#include "bmpwrite.h"

using namespace OpenGP; ///< for Vec3
using namespace std;

using Colour = Vec3; ///< RGB Value
Colour red() { return Colour(1.0f, 0.0f, 0.0f); }
Colour pink() { return Colour(1.0f, 0.71f, 0.757f); }
Colour blueviolet() { return Colour(0.541f, 0.169f, .886f); }
Colour thistle() { return Colour(0.847f, 0.749f, 0.847f); }
Colour green() { return Colour(0.0f, 0.0f, 1.0f); }
Colour white() { return Colour(1.0f, 1.0f, 1.0f); }
Colour black() { return Colour(0.0f, 0.0f, 0.0f); }
Colour grey() { return Colour(0.09f, 0.09f, 0.09f); }
Colour lightgrey() { return Colour(0.19f, 0.19f, 0.19f); }
Colour lightblue() { return Colour(0.4f, 0.9f, 1.0f); }
Colour blue() { return Colour(0.0f, 0.0f, 1.0f); }

/**
 * The Sphere struct
 */
struct Sphere {
    Vec3 pos; ///< position
    float radius;
    Colour colour;
    Vec3 EsubC; ///< camera center - sphere center
};

/**
 * The Plane struct
 */
struct Plane {
    Vec3 point; ///< point on plane
    Vec3 normal;
    Colour colour;
};

/**
 * The Light struct
 */
struct Light {
    Vec3 pos; ///< position
    float lumin; ///< luminosity (or light intensity)
};

/**
 * A function that returns the time along ray if denominator is not zero in ray-plane intersection equation
 */
float planeIntersect(Plane plane, Vec3 ray, Vec3 E) {
    float denom = ray.dot(plane.normal); float t;

    if (denom != 0){ ///< there is an intersection
        Vec3 EsubP = E - plane.point;
        return t = - EsubP.dot(plane.normal)/denom;
    } else
        return t = -1;
}

/**
 * A function that returns the discriminant of a ray-sphere intersection equation
 */
float sphereIntersect(Vec3 E, Vec3 ray, Sphere sphere){
    /// ray sphere intersection
    float disc;
    Vec3 EsubC = E - sphere.pos;
    return disc = std::powf(ray.dot(EsubC),2) - EsubC.dot(EsubC) + sphere.radius*sphere.radius;
}

/**
 * A function that returns a color with ambient, diffuse, and specular shadding according to the Blinn-Phong reflection model
 */
Colour blinnPhongShade(Vec3 pos, Vec3 ray, Vec3 normal, Light l1, Light l2, Sphere s){
    Colour c;
    Vec3 lightDir = l1.pos - pos; lightDir = lightDir.normalized();
    Vec3 lightDir2 = l2.pos - pos; lightDir2 = lightDir2.normalized();
    /// add diffuse shading
    c = std::fmax(normal.dot(lightDir), 0.0f)*l1.lumin*s.colour;
    //c= c + std::fmax(normal.dot(lightDir2), 0.0f)*l2.lumin*s.colour;
    c= c + std::fmax(normal.dot(lightDir2), 0.0f)*(std::fmin(l2.lumin, 0.05))*s.colour;
    /// add violet ambient light
    c = c + blueviolet()*0.9f;
    /// add specular light: (unit) direction from surface point to light,
    /// you need is the direction of the light relative to the surface
    Vec3 h; /// Blinn - Phong Model (half-vector)
    h = (lightDir + -1.0f*ray).normalized();
    c = c + std::powf(std::fmax(normal.dot(h), 0.0f), 1000.0f)*l1.lumin*s.colour;
    h = (lightDir2 + -1.0f*ray).normalized();
    return c + std::powf(std::fmax(normal.dot(h), 0.0f), 1000.0f)*l2.lumin*s.colour;
}

/**
 * A function that returns a color with ambient and diffuse shadding (for a plane)
 */
Colour ambDifShade(Plane p, Vec3 lightDir, Light l){
    Colour c;
    /// add diffuse shading
    c = std::fmax(p.normal.dot(lightDir), 0.0f)*l.lumin*p.colour;
    /// add violet ambient light
    return c + blueviolet()*0.5f;
}

/**
 * A function that prints out a ray
 */
void printRay(Vec3 ray){
    cout << ray(0) << " " << ray(1) << " " << ray(2) << endl;
}

int main(int, char**){

    int wResolution = 640;
    int hResolution = 480;
    float aspectRatio = float(wResolution) / float(hResolution);

    /// #rows = hResolution, #cols = wResolution
    Image<Colour> image(hResolution, wResolution); ///< creates image object

    /**
     * This camera is defined by a position and an orientation (up and forward directions), and allows us
     * to create a primary ray for each pixel in the output image.
     */
    Vec3 W = Vec3(0.0f, 0.0f, -1.0f); ///< viewing direction
    Vec3 V = Vec3(0.0f, 1.0f, 0.0f);
    Vec3 U = Vec3(1.0f, 0.0f, 0.0f);
    float d = 2.0f; ///< focal length
    Vec3 E = -d*W; ///< eye

    float left = -1.0f*aspectRatio;
    float right = 1.0f*aspectRatio;
    float bottom = -1.0f;
    float top = 1.0f;

    /// Sphere position
    Sphere s1; Sphere s2;
    s1.pos = Vec3(-0.3f, -0.4f, -0.6f);
    s1.radius = 0.4f;
    s1.colour = pink();
    s1.EsubC = E - s1.pos; ///< camera center - sphere center
    s2.pos = Vec3(0.2f, -0.1f, -1.1f);
    s2.radius = 0.3f;
    s2.colour = lightblue();
    s2.EsubC = E - s2.pos;


    /**
     * Plane position
     * A plane can be defined as a point representing how far the plane is from the world origin and
     * a normal (defining the orientation of the plane).
     */
    Plane pl1; ///< floor plane
    pl1.point = Vec3(0.0f, -1.0f, -5.0f);
    pl1.normal = Vec3(0.0f, 1.0f, 0.0f);
    pl1.normal = pl1.normal.normalized();
    pl1.colour = thistle();

    Plane leftwall;
    leftwall.point = Vec3(-1.3f, 0.0f, -3.0f);
    leftwall.normal = Vec3(1.0f, 0.0f, 0.0f);
    leftwall.normal = leftwall.normal.normalized();
    leftwall.colour = white();

    Plane rightwall;
    rightwall.point = Vec3(1.3f, 0.0f, -3.0f);
    rightwall.normal = Vec3(-1.0f, 0.0f, 0.0f);
    rightwall.normal = rightwall.normal.normalized();
    rightwall.colour = white();

    Plane backwall;
    backwall.point = Vec3(0.0f, 1.0f, -5.0f);
    backwall.normal = Vec3(0.0f, 0.0f, 1.0f);
    backwall.normal = backwall.normal.normalized();
    backwall.colour = thistle();

    Plane ceiling;
    ceiling.point = Vec3(0.0f, 1.0f, -5.0f);
    ceiling.normal = Vec3(0.0f, -1.0f, 0.0f);
    ceiling.normal = ceiling.normal.normalized();
    ceiling.colour = thistle();

    /// Light positions
    Light light1; Light light2;
    light1.pos = Vec3(0.50f, 2.0f, 2.0f);
    light1.lumin = 1.0f;
    light2.pos = Vec3(-1.0f, 2.0f, 1.0f);
    light2.lumin = 0.5f;

    /// loops over the image object created above
    for (int row = 0; row < image.rows(); ++row) {
        for (int col = 0; col < image.cols(); ++col) {

            /**
             * build primary rays
             * compute the origin and direction of each pixel's viewing ray based on the camera geometry
             * The origin of each ray is the position of the pinhole ((0,0,0) in our simplfication), and
             * the direction is the pixel position in the image plane minus the pinhole position.
            */

            /// 3D location of pixel we want
            Vec3 pixel = left*U + (col*(right-left)/image.cols())*U;
            pixel += bottom*V + (row*(top-bottom)/image.rows())*V;

            /// build primary ray direction
            Vec3 ray = pixel - E;
            ray = ray.normalized();

            /**
             * ray sphere intersection and shading
             * finds the closest object intersecting the viewing ray, shading computes the pixel color
             * based on the results of ray intersection.
             * solve discriminant --- if neg not touching, if 0 then touches at one point,
             * if greater than zero then line touches the sphere in two points
             *
             * for shadows find where it hits the object and then find if it hits other objects on the way
             * to the light, if it does then you dont light the pixel with diffuse light
             */

            /// ray sphere intersections
            float disc;
            disc = sphereIntersect(E, ray, s1);

            float disc2;
            disc2 = sphereIntersect(E, ray, s2);

            /// ray plane intersections
            bool intersect; float t;
            t = planeIntersect(pl1, ray, E);
            intersect = (t>=0);

            bool intersectL; float tL;
            tL = planeIntersect(leftwall, ray, E);
            intersectL = (tL>=0);

            bool intersectR; float tR;
            tR = planeIntersect(rightwall, ray, E);
            intersectR = (tR>=0);

            bool intersectC; float tC;
            tC = planeIntersect(ceiling, ray, E);
            intersectC = (tC>=0);

            bool intersectB; float tB;
            tB = planeIntersect(backwall, ray, E);
            intersectB = (tB>=0);

            /// shading pixel, sphere1 intersection
            if (disc >= 0)
            {
                /// time along the ray vector
                float ts = -ray.dot(s1.EsubC) - std::sqrtf(disc);
                /// actual position on sphere
                Vec3 pos = E + ts*ray;
                Vec3 normal = (pos - s1.pos) / s1.radius;
                Colour c;
                c = blinnPhongShade(pos, ray, normal, light1, light2, s1);
                image(row, col) = c;
            }
            /// sphere2 intersection
            else if (disc2 >= 0)
            {
                /// \var time along the ray vector
                float ts = -ray.dot(s2.EsubC) - std::sqrtf(disc2);
                /// \var actual position on sphere
                Vec3 pos = E + ts*ray;
                Vec3 normal = (pos - s2.pos) / s2.radius;
                Colour c;
                c = blinnPhongShade(pos, ray, normal, light1, light2, s2);
                image(row, col) = c;
            }
            /// plane intersection: If ray dot normal to plane != 0 there is a single point of intersection.
            else
            {
                Vec3 posL = E + tL*ray;
                Vec3 posR = E + tR*ray;
                Vec3 posB = E + tB*ray;
                Vec3 posC = E + tC*ray;

                /// check for floor
                if(intersect)
                {
                    /// actual position on plane
                    Vec3 pos = E + t*ray;
                    /// Send a ray from intersection point to light source, build shadow ray direction
                    Vec3 shadowRay = light1.pos - pos;
                    shadowRay = shadowRay.normalized();
                    Vec3 shadowRay2 = light2.pos - pos;
                    shadowRay2 = shadowRay2.normalized();
                    float shadowDisc; float shadowDisc2;
                    shadowDisc = sphereIntersect(pos, shadowRay, s1); ///< first light
                    shadowDisc2 = sphereIntersect(pos, shadowRay, s2);
                    float shadowDisc3; float shadowDisc4;
                    shadowDisc3 = sphereIntersect(pos, shadowRay2, s1); ///< second light
                    shadowDisc4 = sphereIntersect(pos, shadowRay2, s2);

                    /// sphere1 intersection with first light i.e. pixel in shadow
                    if (shadowDisc >= 0 )
                    {
                        /// only add ambient light
                        image(row, col) = grey();
                        image(row, col) = image(row, col) + blueviolet()*0.2f;
                        /// both lights hit sphere, double shadow
                        if(shadowDisc >= 0 && shadowDisc3 >=0 )
                        {
                            image(row, col) = black();
                            image(row, col) = image(row, col) + blueviolet()*0.2f;
                        }
                    }

                    /// sphere2 intersection with first light i.e. pixel in shadow
                    else if(shadowDisc2 >=0)
                    {
                        /// only add ambient light
                        image(row, col) = grey();
                        image(row, col) = image(row, col) + blueviolet()*0.2f;
                        /// both lights hit sphere, double shadow
                        if(shadowDisc2 >= 0 && shadowDisc4 >=0)
                        {
                            image(row, col) = black();
                            image(row, col) = image(row, col) + blueviolet()*0.2f;
                        }
                    }

                    /// sphere1 intersection with second light i.e. pixel in shadow
                    else if (shadowDisc3 >= 0 )
                    {
                        /// only add ambient light
                        image(row, col) = lightgrey();
                        image(row, col) = image(row, col) + blueviolet()*0.2f;
                        /// both lights hit sphere, double shadow
                        if(shadowDisc >= 0 && shadowDisc3 >=0 )
                        {
                            image(row, col) = black();
                            image(row, col) = image(row, col) + blueviolet()*0.2f;
                        }
                    }

                    /// sphere2 intersection with second light i.e. pixel in shadow
                    else if(shadowDisc4 >=0)
                    {
                        /// only add ambient light
                        image(row, col) = lightgrey();
                        image(row, col) = image(row, col) + blueviolet()*0.2f;
                        /// both lights hit sphere, double shadow
                        if(shadowDisc2 >= 0 && shadowDisc4 >=0)
                        {
                            image(row, col) = black();
                            image(row, col) = image(row, col) + blueviolet()*0.2f;
                        }
                    }

                    /// no instersection, thus no shadow from spheres on plane
                    else
                    {
                        Vec3 lightDir = light1.pos - pos;
                        lightDir = lightDir.normalized();
                        Colour c;
                        c = ambDifShade(pl1, lightDir, light1);
                        image(row, col) = c;
                    }
                }

                /// check for back wall
                if (intersectB && -1 < posB(1) && posB(1) < 1)
                {
                    Vec3 lightDir = light1.pos - posB;
                    lightDir = lightDir.normalized();
                    Colour c;
                    c = ambDifShade(backwall, lightDir, light1);
                    image(row, col) = c;
                }

                /// check for ceiling
                if (intersectC && 0.99f < posC(1) && -5 < posC(2))
                {
                    Vec3 lightDir = light1.pos - posC;
                    lightDir = lightDir.normalized();
                    Colour c;
                    c = ambDifShade(ceiling, lightDir, light1);
                    image(row, col) = c;
                }

                /// check for left wall
                if (intersectL && -1 < posL(1) && posL(1) < 1 && -5 < posL(2))
                {
                    Vec3 lightDir = light1.pos - posL;
                    lightDir = lightDir.normalized();
                    Colour c;
                    c = ambDifShade(leftwall, lightDir, light1);
                    image(row, col) = c;
                }

                /// check for right wall
                if (intersectR && -1 < posR(1) && posR(1) < 1 && -5 < posR(2))
                {
                    Vec3 lightDir = light1.pos- posR;
                    lightDir = lightDir.normalized();
                    Colour c;
                    c = ambDifShade(rightwall, lightDir, light1);
                    image(row, col) = c;
                }

           }

            // anti-alias dot multiple rays per pixel going through different points in the tiny pixel
            // square and take the average of those rays
       }
    }

    bmpwrite("../../out.bmp", image);
    imshow(image);

    return EXIT_SUCCESS;
}
