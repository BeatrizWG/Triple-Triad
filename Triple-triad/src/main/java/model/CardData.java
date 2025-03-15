package model;

import java.awt.image.BufferedImage;

public class CardData {
     
    private BufferedImage image;
    private int cardId;
    private String name;
    private int up;
    private int down;
    private int left;
    private int right;
    private String type;
    private BufferedImage typeIcon;
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getCardId() {
        return this.cardId;
    }

    public String getName() {
        return this.name;
    }

    public int getUp() {
        return this.up;
    }

   public int getDown() {
        return this.down;
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public String getType() {
        return this.type;
    }

    public BufferedImage getTypeIcon() {
        return this.typeIcon;
    }

    public void setImage(final BufferedImage image) {
        this.image = image;
    }

    public void setCardId(final int cardId) {
        this.cardId = cardId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUp(final int up) {
        this.up = up;
    }

    public void setDown(final int down) {
        this.down = down;
    }

    public void setLeft(final int left) {
        this.left = left;
    }

    public void setRight(final int right) {
        this.right = right;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setTypeIcon(final BufferedImage typeIcon) {
        this.typeIcon = typeIcon;
    }
    
	public void incrementValues() {
		if (this.up < 10) this.up++;
		if (this.down < 10) this.down++;
		if (this.left < 10) this.left++;
		if (this.right < 10) this.right++;
	}
}