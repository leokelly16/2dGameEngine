package jade;

import java.awt.event.KeyEvent;
import java.security.Key;

public class LevelEditorScene extends Scene{

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("Inside LevelEditorScene");
    }

    @Override
    public void update(float dt){
        /*System.out.println("" + (1.0f/dt) + " FPS");*/
        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changingScene = true;
        }
        if(changingScene && timeToChangeScene > 0.0f){
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;

        }
        else if(changingScene){
            Window.changeScene(1);
        }
    }
}
