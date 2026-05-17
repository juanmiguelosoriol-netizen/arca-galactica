package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * CLase base que representa a una entidad del juego como el protagonista o los enemigos
 * contiene todos los atributos y metodos comunes para heredar a las clases hijas.
 *
 * @author Juan Miguel Osorio Lopez, Juan Daniel Pineda casas y Juan Jose Ospina Guzman
 * @version 1.0
 */

public abstract class Entity {
    /**
     * Atributos para declarar posiciones como: Nula, izquierda, derecha, arriba y abajo.
     */
    public static final int DIR_NONE = 0;

    public static final int DIR_LEFT = 1;

    public static final int DIR_RIGHT = 2;

    public static final int DIR_UP = 3;

    public static final int DIR_DOWN = 4;
    /**
     * Atributos para declarar: Posiciones en el eje X y Y, anchura, altura, el sprite, el estado activo y la direccion.
     */
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage sprite;
    private boolean active;
    private int direction;

    /**
     * Constructor de la clase Entity.
     *
     * @param x      posicion en el eje x
     * @param y      posicion en el eje y
     * @param sprite sprite de la imagen con la validacion de que no sea nula
     */
    public Entity(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.active = true;
        this.direction = DIR_NONE;

        if (sprite != null) {
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
        }
    }

    /**
     * Método abstracto que cada entidad debe implementar.
     * Aquí se define cómo se actualiza cada objeto en el juego.
     */
    public abstract void update();

    /**
     * Metodo para verificar la colision.
     *
     * @return la colision
     */
    public Rectangle getHitBox() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Metodo para predecir una posible colision futura.
     *
     * @param px
     * @param py
     * @return la colision
     */
    public Rectangle getHitBox(int px, int py) {
        return new Rectangle(px, py, width, height);
    }

    /**
     * Calcula el desplazamiento horizontal según la dirección actual.
     * Devuelve -1, 0 o +1.
     */
    public int calculateDx() {
        if (direction == DIR_LEFT) return -1;
        if (direction == DIR_RIGHT) return 1;
        return 0;
    }

    /**
     * Calcula el desplazamiento vertical según la dirección actual.
     * Devuelve -1, 0 o +1.
     */
    public int calculateDy() {
        if (direction == DIR_UP) return -1;
        if (direction == DIR_DOWN) return 1;
        return 0;
    }

    /**
     * Metodo para cargar una imagen desde la carpeta de recursos.
     *
     * @param nombre
     * @return la imagen
     */
    public static BufferedImage uploadImage(String nombre) {
        try {
            InputStream is = Entity.class.getResourceAsStream("/Images/" + nombre);
            if (is == null) {
                System.out.println("Imagen no encontrada: " + nombre);
                return null;
            }
            return ImageIO.read(is);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + nombre);
            return null;
        }
    }
    //getters y setters atributos

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

