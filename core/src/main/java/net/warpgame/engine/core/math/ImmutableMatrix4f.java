package net.warpgame.engine.core.math;

import org.joml.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.text.NumberFormat;

/**
 * @author MarconZet, Jaca777
 * Created 16.08.2018
 */
public class ImmutableMatrix4f extends Matrix4f {

    @Override
    public Matrix4f assume(int properties) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f determineProperties() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m00(float m00) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m01(float m01) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m02(float m02) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m03(float m03) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m10(float m10) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m11(float m11) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m12(float m12) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m13(float m13) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m20(float m20) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m21(float m21) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m22(float m22) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m23(float m23) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m30(float m30) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m31(float m31) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m32(float m32) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f m33(float m33) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m00(float m00) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m01(float m01) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m02(float m02) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m03(float m03) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m10(float m10) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m11(float m11) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m12(float m12) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m13(float m13) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m20(float m20) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m21(float m21) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m22(float m22) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m23(float m23) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m30(float m30) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m31(float m31) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m32(float m32) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f _m33(float m33) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f identity() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix4fc m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix4x3fc m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix4dc m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Matrix3fc mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(AxisAngle4d axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Quaternionfc q) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Quaterniondc q) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set3x3(Matrix4f mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set4x3(Matrix4x3fc mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set4x3(Matrix4f mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul(Matrix4fc right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulLocal(Matrix4fc left) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulLocalAffine(Matrix4fc left) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul(Matrix4x3fc right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul(Matrix3x2fc right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4fc view) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulPerspectiveAffine(Matrix4x3fc view) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulAffineR(Matrix4fc right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulAffine(Matrix4fc right) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulOrthoAffine(Matrix4fc view) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f fma4x3(Matrix4fc other, float otherFactor) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f add(Matrix4fc other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f sub(Matrix4fc subtrahend) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mulComponentWise(Matrix4fc other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f add4x3(Matrix4fc other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f sub4x3(Matrix4f subtrahend) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f mul4x3ComponentWise(Matrix4fc other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(float[] m, int off) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(float[] m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(FloatBuffer buffer) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(ByteBuffer buffer) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFromAddress(long address) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set(Vector4fc col0, Vector4fc col1, Vector4fc col2, Vector4fc col3) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invert() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertPerspective() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertFrustum() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertOrtho() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f invertAffine() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transpose3x3() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f transpose() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translation(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translation(Vector3fc offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setTranslation(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setTranslation(Vector3fc xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public String toString(NumberFormat formatter) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f zero() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(float factor) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaling(Vector3fc xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(float angle, Vector3fc axis) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(float angle, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationX(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationY(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationZ(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationTowardsXY(float dirX, float dirY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRotationYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotation(Quaternionfc quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(Vector3fc translation, Quaternionfc quat, Vector3fc scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScale(Vector3fc translation, Quaternionfc quat, float scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleInvert(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleInvert(Vector3fc translation, Quaternionfc quat, Vector3fc scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleInvert(Vector3fc translation, Quaternionfc quat, float scale) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(float tx, float ty, float tz, float qx, float qy, float qz, float qw, float sx, float sy, float sz, Matrix4f m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateScaleMulAffine(Vector3fc translation, Quaternionfc quat, Vector3fc scale, Matrix4f m) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotate(float tx, float ty, float tz, float qx, float qy, float qz, float qw) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotate(float tx, float ty, float tz, Quaternionfc quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f set3x3(Matrix3fc mat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scale(Vector3fc xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scale(float xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scale(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleAround(float sx, float sy, float sz, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleAround(float factor, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleLocal(float xyz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleLocal(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleAroundLocal(float sx, float sy, float sz, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f scaleAroundLocal(float factor, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateX(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateY(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateZ(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateTowardsXY(float dirX, float dirY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateXYZ(Vector3f angles) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineXYZ(float angleX, float angleY, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateZYX(Vector3f angles) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineZYX(float angleZ, float angleY, float angleX) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateYXZ(Vector3f angles) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffineYXZ(float angleY, float angleX, float angleZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffine(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateLocal(float ang, float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateLocalX(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateLocalY(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateLocalZ(float ang) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translate(Vector3fc offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translate(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translateLocal(Vector3fc offset) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translateLocal(float x, float y, float z) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetric(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f orthoSymmetricLH(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetric(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetricLH(float width, float height, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrthoSymmetricLH(float width, float height, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho2D(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f ortho2DLH(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho2D(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setOrtho2DLH(float left, float right, float bottom, float top) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAlong(Vector3fc dir, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAlong(Vector3fc dir, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAlong(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAt(Vector3fc eye, Vector3fc center, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAt(Vector3fc eye, Vector3fc center, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setLookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAtLH(Vector3fc eye, Vector3fc center, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lookAtLH(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspective(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspective(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f perspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setPerspectiveLH(float fovy, float aspect, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustum(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f frustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar, boolean zZeroToOne) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFrustumLH(float left, float right, float bottom, float top, float zNear, float zFar) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setFromIntrinsic(float alphaX, float alphaY, float gamma, float u0, float v0, int imgWidth, int imgHeight, float near, float far) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(Quaternionfc quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAffine(Quaternionfc quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAround(Quaternionfc quat, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateLocal(Quaternionfc quat) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateAroundLocal(Quaternionfc quat, float ox, float oy, float oz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(AxisAngle4f axisAngle) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotate(float angle, Vector3fc axis) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(float nx, float ny, float nz, float px, float py, float pz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(Vector3fc normal, Vector3fc point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflect(Quaternionfc orientation, Vector3fc point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(float nx, float ny, float nz, float px, float py, float pz) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(Vector3fc normal, Vector3fc point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f reflection(Quaternionfc orientation, Vector3fc point) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setRow(int row, Vector4fc src) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f setColumn(int column, Vector4fc src) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f normal() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f normalize3x3() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(Vector4f light, float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, float a, float b, float c, float d) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(Vector4f light, Matrix4f planeTransform) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f shadow(float lightX, float lightY, float lightZ, float lightW, Matrix4f planeTransform) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardCylindrical(Vector3fc objPos, Vector3fc targetPos, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardSpherical(Vector3fc objPos, Vector3fc targetPos, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f billboardSpherical(Vector3fc objPos, Vector3fc targetPos) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f pick(float x, float y, float width, float height, int[] viewport) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f swap(Matrix4f other) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f arcball(float radius, float centerX, float centerY, float centerZ, float angleX, float angleY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f arcball(float radius, Vector3fc center, float angleX, float angleY) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f trapezoidCrop(float p0x, float p0y, float p1x, float p1y, float p2x, float p2y, float p3x, float p3y) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f lerp(Matrix4fc other, float t) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateTowards(Vector3fc dir, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotateTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationTowards(Vector3fc dir, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f rotationTowards(float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateTowards(Vector3fc pos, Vector3fc dir, Vector3fc up) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f translationRotateTowards(float posX, float posY, float posZ, float dirX, float dirY, float dirZ, float upX, float upY, float upZ) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f affineSpan(Vector3f corner, Vector3f xDir, Vector3f yDir, Vector3f zDir) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }

    @Override
    public Matrix4f obliqueZ(float a, float b) {
        throw new UnsupportedOperationException("Identity matrix is immutable");
    }
}
