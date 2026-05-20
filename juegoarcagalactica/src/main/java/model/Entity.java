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
     * Direccion sin movimiento.
     */
    public static final int DIR_NONE = 0;

    /**
     * Direccion hacia la izquierda.
     */
    public static final int DIR_LEFT = 1;

    /**
     * Direccion hacia la derecha.
     */
    public static final int DIR_RIGHT = 2;

    /**
     * Direccion hacia arriba.
     */
    public static final int DIR_UP = 3;

    /**
     * Direccion hacia abajo.
     */
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
     * Calcula el desplazamiento horizontal según la dirección actual.
     *
     * @return -1 si va a la izquierda, 1 si va a la derecha o 0 si no se mueve horizontalmente
     */
    public int calculateDx() {
        if (direction == DIR_LEFT) return -1;
        if (direction == DIR_RIGHT) return 1;
        return 0;
    }

    /**
     * Calcula el desplazamiento vertical según la dirección actual.
     *
     * @return -1 si va hacia arriba, 1 si va hacia abajo o 0 si no se mueve verticalmente
     */
    public int calculateDy() {
        if (direction == DIR_UP) return -1;
        if (direction == DIR_DOWN) return 1;
        return 0;
    }

    /**
     * Metodo para cargar una imagen desde la carpeta de recursos.
     *
     * @param nombre nombre del archivo de imagen
     * @return imagen cargada o null si no se encuentra
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

    /**
     * Indica si la entidad esta activa.
     *
     * @return true si esta activa, false si no
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Cambia el estado activo de la entidad.
     *
     * @param active nuevo estado activo
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Retorna la direccion actual.
     *
     * @return direccion actual
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Cambia la direccion actual.
     *
     * @param direction nueva direccion
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Retorna el alto de la entidad.
     *
     * @return alto de la entidad
     */
    public int getHeight() {
        return height;
    }

    /**
     * Cambia el alto de la entidad.
     *
     * @param height nuevo alto
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Retorna el sprite de la entidad.
     *
     * @return imagen de la entidad
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    /**
     * Cambia el sprite de la entidad.
     *
     * @param sprite nuevo sprite
     */
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            this.width = sprite.getWidth();
            this.height = sprite.getHeight();
        }
    }

    /**
     * Retorna el ancho de la entidad.
     *
     * @return ancho de la entidad
     */
    public int getWidth() {
        return width;
    }

    /**
     * Cambia el ancho de la entidad.
     *
     * @param width nuevo ancho
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Retorna la posicion en x.
     *
     * @return posicion en x
     */
    public int getX() {
        return x;
    }

    /**
     * Cambia la posicion en x.
     *
     * @param x nueva posicion en x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna la posicion en y.
     *
     * @return posicion en y
     */
    public int getY() {
        return y;
    }

    /**
     * Cambia la posicion en y.
     *
     * @param y nueva posicion en y
     */
    public void setY(int y) {
        this.y = y;
    }
}

