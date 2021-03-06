package io.github.moulberry.notenoughupdates.core.config;

import com.google.gson.annotations.Expose;
import net.minecraft.client.gui.ScaledResolution;

public class Position {

    @Expose
    private int x;
    @Expose
    private int y;
    @Expose
    private boolean centerX;
    @Expose
    private boolean centerY;

    private static final int EDGE_OFFSET = 0;

    public Position(int x, int y) {
        this(x, y, false, false);
    }

    public Position(int x, int y, boolean centerX, boolean centerY) {
        this.x = x;
        this.y = y;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public Position clone() {
        return new Position(x, y);
    }

    public boolean isCenterX() {
        return centerX;
    }

    public boolean isCenterY() {
        return centerY;
    }

    public int getRawX() {
        return x;
    }

    public int getRawY() {
        return y;
    }

    public int getAbsX(ScaledResolution scaledResolution, int objWidth) {
        int width = scaledResolution.getScaledWidth();

        if(centerX) {
            return width/2 + x;
        }

        int ret = x;
        if(x < 0) {
            ret = width + x;
        }

        if(ret < 0) ret = 0;
        if(ret > width) ret = width - objWidth;

        return ret;
    }

    public int getAbsY(ScaledResolution scaledResolution, int objHeight) {
        int height = scaledResolution.getScaledHeight();

        if(centerY) {
            return height/2 + y;
        }

        int ret = y;
        if(y < 0) {
            ret = height + y;
        }

        if(ret < 0) ret = 0;
        if(ret > height) ret = height - objHeight;

        return ret;
    }

    public int moveX(int deltaX, int objWidth, ScaledResolution scaledResolution) {
        int screenWidth = scaledResolution.getScaledWidth();
        boolean wasPositiveX = this.x >= 0;
        this.x += deltaX;

        if(centerX) {
            if(wasPositiveX) {
                if(this.x > screenWidth/2-objWidth) {
                    deltaX += screenWidth/2-objWidth-this.x;
                    this.x = screenWidth/2-objWidth;
                }
            } else {
                if(this.x < -screenWidth/2) {
                    deltaX += -screenWidth/2-this.x;
                    this.x = -screenWidth/2;
                }
            }
            return deltaX;
        }

        if(wasPositiveX) {
            if(this.x < EDGE_OFFSET) {
                deltaX += EDGE_OFFSET-this.x;
                this.x = EDGE_OFFSET;
            }
            if(this.x > screenWidth-EDGE_OFFSET) {
                deltaX += screenWidth-EDGE_OFFSET-this.x;
                this.x = screenWidth-EDGE_OFFSET;
            }
        } else {
            if(this.x+objWidth > -EDGE_OFFSET) {
                deltaX += -EDGE_OFFSET-objWidth-this.x;
                this.x = -EDGE_OFFSET-objWidth;
            }
            if(this.x+screenWidth < EDGE_OFFSET) {
                deltaX += EDGE_OFFSET-screenWidth-this.x;
                this.x = EDGE_OFFSET-screenWidth;
            }
        }

        if(this.x >= 0 && this.x+objWidth/2 > screenWidth/2) {
            this.x -= screenWidth;
        }
        if(this.x < 0 && this.x+objWidth/2 <= -screenWidth/2) {
            this.x += screenWidth;
        }
        return deltaX;
    }

    public int moveY(int deltaY, int objHeight, ScaledResolution scaledResolution) {
        int screenHeight = scaledResolution.getScaledHeight();
        boolean wasPositiveY = this.y >= 0;
        this.y += deltaY;

        if(centerY) {
            if(wasPositiveY) {
                if(this.y > screenHeight/2-objHeight) {
                    deltaY += screenHeight/2-objHeight-this.y;
                    this.y = screenHeight/2-objHeight;
                }
            } else {
                if(this.y < -screenHeight/2) {
                    deltaY += -screenHeight/2-this.y;
                    this.y = -screenHeight/2;
                }
            }
            return deltaY;
        }

        if(wasPositiveY) {
            if(this.y < EDGE_OFFSET) {
                deltaY += EDGE_OFFSET-this.y;
                this.y = EDGE_OFFSET;
            }
            if(this.y > screenHeight-EDGE_OFFSET) {
                deltaY += screenHeight-EDGE_OFFSET-this.y;
                this.y = screenHeight-EDGE_OFFSET;
            }
        } else {
            if(this.y+objHeight > -EDGE_OFFSET) {
                deltaY += -EDGE_OFFSET-objHeight-this.y;
                this.y = -EDGE_OFFSET-objHeight;
            }
            if(this.y+screenHeight < EDGE_OFFSET) {
                deltaY += EDGE_OFFSET-screenHeight-this.y;
                this.y = EDGE_OFFSET-screenHeight;
            }
        }

        if(this.y >= 0 && this.y-objHeight/2 > screenHeight/2) {
            this.y -= screenHeight;
        }
        if(this.y < 0 && this.y-objHeight/2 <= -screenHeight/2) {
            this.y += screenHeight;
        }
        return deltaY;
    }

}
