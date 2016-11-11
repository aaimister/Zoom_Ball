package com.splitseed.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    public GlyphLayout layout;

    public TextureAtlas textureAtlas;
    public AssetManager assetManager;

    public TextureRegion[] loadBar;
    public TextureRegion[] zoomBallLogo;
    public TextureRegion[] splitSeedLogo;

    public BitmapFont font;

    public final Color GREEN = getColor(45.0f, 166.0f, 76.0f);
    public final Color RED = getColor(166.0f, 45.0f, 48.0f);
    public final Color GREY = getColor(50.0f, 50.0f, 50.0f);
    public final Color BLUE = getColor(5.0f, 90.0f, 167.0f);
    public final Color ORANGE = getColor(190.0f, 80.0f, 0.0f);

    public Assets() {
        assetManager = new AssetManager();
        layout = new GlyphLayout();

        TextureAtlasLoader.TextureAtlasParameter param = new TextureAtlasLoader.TextureAtlasParameter();
        param.flip = true;
        assetManager.load("data/asset manager/spload output/512.atlas", TextureAtlas.class, param);
        assetManager.load("data/font/SuperWebcomicBros.fnt", BitmapFont.class);
    }

    public void setupSpload() {
        textureAtlas = assetManager.get("data/asset manager/spload output/512.atlas");

        loadBar = new TextureRegion[] {
                textureAtlas.findRegion("fill"), textureAtlas.findRegion("outline"),
        };
        setTextureFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear, loadBar);

        zoomBallLogo = new TextureRegion[] {
                textureAtlas.findRegion("circle")
        };
        setTextureFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear, zoomBallLogo);

        splitSeedLogo = new TextureRegion[] {
                textureAtlas.findRegion("whole"), textureAtlas.findRegion("split"), textureAtlas.findRegion("text")
        };
        setTextureFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear, splitSeedLogo);

        font = assetManager.get("data/font/SuperWebcomicBros.fnt", BitmapFont.class);
        font.setFixedWidthGlyphs("-=");
    }

    public void loadRest() {
//        TextureAtlasLoader.TextureAtlasParameter param = new TextureAtlasLoader.TextureAtlasParameter();
//        param.flip = true;
//        assetManager.load("data/asset manager/rest output/512.atlas", TextureAtlas.class, param);
    }

    public void setupRest() {
        // setup the rest of the assets
    }

    public Color getColor(float r, float g, float b) {
        return getColor(r, g, b, 1);
    }

    public Color getColor(float r, float g, float b, float a) {
        return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a);
    }

    public void setTextureFilter(Texture.TextureFilter minFilter, Texture.TextureFilter maxFilter, TextureRegion ...list) {
        for (TextureRegion tr : list) { tr.getTexture().setFilter(minFilter, maxFilter); }
    }

    public void dispose() {
        textureAtlas.dispose();
        assetManager.dispose();
        font.dispose();
    }

}