package components;


import javafx.scene.image.ImageView;

/**
 * Created by BouzalmatAbderrahman on 6/6/2019
 */
public class Door extends Objects {
    public Door(ImageView image, State state) {
        super(image, state);
    }

    public Door(ImageView image) {
        super(image);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
