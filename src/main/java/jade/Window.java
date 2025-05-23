package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;

    private long glfwWindow;

    private float r, g, b, a;
    private boolean fadeToBlack = false;
    private static Window window = null;
    private  Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Game";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run(){
        System.out.println("Game Started: " + title + "\nLWJGL Version: " + Version.getVersion());

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate glfw and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        // Set up error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW to set up window
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make OpenGL the current context
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // Get C bindings to OpenGL
        GL.createCapabilities();

    }

    public void loop(){
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBlack){
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }
            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                fadeToBlack = true;
            }
            glfwSwapBuffers(glfwWindow);
        }
    }

}
