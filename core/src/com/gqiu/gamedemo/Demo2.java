package com.gqiu.gamedemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.util.ArrayList;
import java.util.List;

public class Demo2 extends ApplicationAdapter {
    private PerspectiveCamera camera;
    private List<ModelInstance> instances = new ArrayList<ModelInstance>();
    private ModelBatch modelBatch;
    private AssetManager assets;
    private Environment environment;
    private boolean loading = true;
    private CameraInputController camController;

    @Override
    public void create() {
        super.create();
        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 7f, 100f);
        camera.translate(3, 0, 8);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 400f;
        camera.update();


        assets = new AssetManager();
        assets.load("Constructor.g3dj", Model.class);

        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);
    }

    private void doneLoading() {
        Model ship = assets.get("Constructor.g3dj", Model.class);
        ModelInstance instance = new ModelInstance(ship);
        instance.transform.setToTranslation(1, 1, 1);
        instances.add(instance);
        loading = false;
    }


    @Override
    public void render() {
        if (loading && assets.update()) {
            doneLoading();
        }
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
