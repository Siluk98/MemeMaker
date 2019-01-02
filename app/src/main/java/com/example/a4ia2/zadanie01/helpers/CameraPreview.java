package com.example.a4ia2.zadanie01.helpers;

/*
import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
    private Camera _camera;
    private SurfaceHolder _surfaceHolder;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this._camera = camera;
        this._surfaceHolder = this.getHolder();
        this._surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        _camera.startPreview();
        _camera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        _camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
*/


import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera _camera;
    private SurfaceHolder _surfaceHolder;
    public CameraPreview(Context context,Camera camera) {
        super(context);
        this._camera = camera;
        this._surfaceHolder = this.getHolder();
        this._surfaceHolder.addCallback(this);
        _camera.setDisplayOrientation(90);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
            _camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        try {
            _camera.setPreviewDisplay(_surfaceHolder);
            _camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
