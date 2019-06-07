package components;


import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import utils.GlobalVars;

/**
 * Created by BouzalmatAbderrahman on 6/6/2019
 */
public class Door extends Objects {
    TranslateTransition translateTransition;
    public Door(ImageView image, State state) {
        super(image, state);
    }

    public Door(ImageView image) {
        super(image);
    }

    @Override
    public void open() {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(getImage());
        translateTransition.setByY(GlobalVars.DOOR_MAX_YPOSITION - GlobalVars.DOOR_MIN_YPOSITION);
        translateTransition.setDuration(GlobalVars.duration);
        translateTransition.play();
        this.setState(State.OPENING);
    }

    @Override
    public void close() {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(getImage());
        translateTransition.setByY(GlobalVars.DOOR_MIN_YPOSITION - GlobalVars.DOOR_MAX_YPOSITION);
        translateTransition.setDuration(GlobalVars.duration);
        translateTransition.play();
        this.setState(State.CLOSING);
    }
}
