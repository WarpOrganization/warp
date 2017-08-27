package pl.warp.test.sketches;

import pl.warp.engine.core.execution.task.SimpleEngineTask;

import java.awt.event.KeyEvent;

/**
 * @author Jaca777
 * Created 2017-08-26 at 15
 */
public class DiSketch {


    //Przykładowe annotacje używane przez DI i zarządzanie kontekstem:
    @interface Service { //Lub context, ale imo serwis jest na tyle generalne że spoko będzie np. w serwerze

    }

    @interface RegisterTask {
        String threadName() default "scriptExecutor";
    }

    @Service
    static class Input {
        //...
        boolean isKeyPressed(int key) {
            //check key
            return true;
        }
    }

    //automatycznie rejestruje Task w executorze po nazwie
    @Service
    @RegisterTask(threadName = "input")
    static class DecoratedInput extends SimpleEngineTask {

        private Input input;

        private int[] pressedTime = new int[255];

        public DecoratedInput(Input input) { //Zależności wypisane w konstruktorze, automatycznie wstrzykiwane
            this.input = input;
        }

        @Override
        public void update(int delta) {
            // read from input and set pressedTime[i]
        }

        boolean wasKeyPressedFor(int key, int time) {
            return pressedTime[key] >= time;
        }

    }


    static class SomeScript /**  extends Script **/ {
        private DecoratedInput input;

        public SomeScript(DecoratedInput input) { //Znowu jawne zależnoścu
            //owner skryptu NIE PRZEKAZYWANY
            this.input = input;
        }

        // @Override
        public void onInit() {
            //inicjalizacja
        }

        //  @Override
        public void onUpdate(int delta) {
            if(input.wasKeyPressedFor(KeyEvent.VK_A, 2000)) {
                System.out.println("A przycisniete przez 2 sekundy!");
            }
        }
    }

    /** Przykład nowego dodawania skryptów:
     *  Component component = ...
     *  component.addScript(SomeScript.class) <- NIE OBIEKT, ALE KLASA
     */

}
