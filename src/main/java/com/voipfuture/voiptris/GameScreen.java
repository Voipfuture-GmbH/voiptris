/**
 * Copyright 2012 Tobias Gierke <tobias.gierke@code-sourcery.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.voipfuture.voiptris;

import com.voipfuture.voiptris.api.GameState;
import com.voipfuture.voiptris.api.IGameController;
import com.voipfuture.voiptris.api.IPlayingField;
import com.voipfuture.voiptris.api.IUserInputProvider;
import com.voipfuture.voiptris.api.TileType;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Optional;

public class GameScreen extends JFrame
{
    private static final Color BORDER_COLOR = new Color(100,100,100);
    private static final Color BACKGROUND_COLOR = new Color(0,0,0 );

    private boolean showGrid = false;

    private final IGameController controller;
    private final MyPanel panel;

    private static final Map<TileType,Color> COLORS =
            Map.of(
                    TileType.TYPE_0,new Color(0,240,240),
                    TileType.TYPE_1,new Color(0,0,240),
                    TileType.TYPE_2,new Color(240,160,0),
                    TileType.TYPE_3,new Color(240,240,0),
                    TileType.TYPE_4,new Color(0,240,0),
                    TileType.TYPE_5,new Color(160,0,240),
                    TileType.TYPE_6,new Color(240,0,0)
                    );

    public GameScreen(IGameController controller)
    {
        super("VoIPTris");
        this.controller = controller;
        this.panel = new MyPanel();

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        getContentPane().add( panel );
        panel.setPreferredSize( new Dimension(506,607) );
        pack();
        setLocationRelativeTo( null );
    }

    public IPlayingField getPlayingField()
    {
        return controller.getPlayingField();
    }

    private final class MyPanel extends JPanel
    {
        private final UserInputProvider inputProvider = new UserInputProvider();

        {
            setBackground( Color.BLACK );
            setFocusable( true );
            inputProvider.attach( this );
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent( g );

            final int borderThickness = 10;
            final int insets = 15;

            int width = (int) (getHeight()*0.9f);
            int cellWidth = width / getPlayingField().height();
            int playingFieldWidth = cellWidth * getPlayingField().width();
            int playingFieldHeight = cellWidth * getPlayingField().height();

            int bottomRightX = getWidth() - borderThickness - insets;
            int topLeftX = bottomRightX - playingFieldWidth;
            int topLeftY = insets+borderThickness;
            int bottomRightY = topLeftY + playingFieldHeight;

            g.setColor( BORDER_COLOR );
            g.fillRect(topLeftX-borderThickness,
                    topLeftY-borderThickness,
                    playingFieldWidth+2*borderThickness,
                    playingFieldHeight+2*borderThickness);
            g.setColor( BACKGROUND_COLOR );
            g.fillRect( topLeftX, topLeftY, playingFieldWidth, playingFieldHeight );

            // render playing field
            final int cellBorder = 2;

            final int[] px = new int[4];
            final int[] py = new int[4];

            for (int y = topLeftY, ty = 0; ty < getPlayingField().height() ; y += cellWidth, ty++)
            {
                for (int x = topLeftX, tx = 0; tx < getPlayingField().width(); x += cellWidth, tx++)
                {
                    final Optional<TileType> tileId = getPlayingField().getTileType( tx,ty );
                    if ( tileId.isPresent() )
                    {
                        final Color color = COLORS.get(tileId.get());

                        // fill inner
                        g.setColor(color);
                        g.fillRect( x,y,cellWidth, cellWidth);

                        // fill top
                        px[0] = x; py[0] = y;
                        px[1] = x+cellWidth; py[1] = y;
                        px[2] = x+cellWidth-cellBorder; py[2] =y+cellBorder;
                        px[3] = x+cellBorder; py[3] = y+cellBorder;
                        g.setColor( color.brighter().brighter() );
                        g.fillPolygon(px,py,4);

                        // fill left
                        px[0] = x; py[0] = y;
                        px[1] = x+cellBorder; py[1] = y+cellBorder;
                        px[2] = x+cellBorder; py[2] = y+cellWidth-cellBorder;
                        px[3] = x; py[3] = y+cellWidth;
                        g.setColor( color.darker() );
                        g.fillPolygon(px,py,4);

                        // fill right
                        px[0] = x+cellWidth; py[0] = y;
                        px[1] = x+cellWidth; py[1] = y+cellWidth;
                        px[2] = x+cellWidth-cellBorder; py[2] = y+cellWidth-cellBorder;
                        px[3] = x+cellWidth-cellBorder; py[3] = y+cellBorder;
                        g.setColor( color.darker() );
                        g.fillPolygon(px,py,4);

                        // fill bottom
                        px[0] = x+cellBorder; py[0] = y+cellWidth-cellBorder;
                        px[1] = x+cellWidth-cellBorder; py[1] = y+cellWidth-cellBorder;
                        px[2] = x+cellWidth; py[2] = y+cellWidth;
                        px[3] = x; py[3] = y+cellWidth;
                        g.setColor( color.darker().darker() );
                        g.fillPolygon(px,py,4);
                    }
                }
            }

            if ( showGrid && controller.getState() != GameState.GAME_OVER )
            {
                g.setColor(Color.WHITE);
                for (int gridY = topLeftY, y = 0; y < getPlayingField().height() ; gridY+= cellWidth,y++)
                {
                    g.drawLine( topLeftX, gridY, bottomRightX, gridY );
                }
                for (int gridX = topLeftX, x = 0; x < getPlayingField().width(); gridX += cellWidth, x++)
                {
                    g.drawLine(gridX,topLeftY,gridX,bottomRightY);
                }
            }

            // render score & level
            final Font font = getFont().deriveFont( 18f );
            g.setColor( Color.WHITE );
            g.setFont(font);
            int lineHeight = getFontHeight( "SCORE" , g  );
            int textY = 5+lineHeight;
            g.drawString( "SCORE: " + controller.getScore(), 15, textY );
            textY += lineHeight;
            g.drawString( "LEVEL: " + controller.getLevel(), 15, textY );
            textY += lineHeight;
            g.drawString( "NEXT LEVEL IN: " + controller.getRowsUntilNextLevel(), 15, textY );
            g.setFont( getFont() );

            switch( controller.getState() ) {
                case PAUSED:
                    Font font2 = getFont().deriveFont( Font.BOLD, 24f );
                    g.setFont( font2 );
                    g.setColor( Color.RED );
                    Rectangle2D bounds = g.getFontMetrics().getStringBounds( "PAUSED", g );
                    int x = topLeftX + playingFieldWidth / 2 - (int) (bounds.getWidth()/2);
                    int y = topLeftY + playingFieldHeight / 2 + (int) (bounds.getHeight()/2);
                    g.drawString( "PAUSED", x, y );
                    break;
                case GAME_OVER:
                    font2 = getFont().deriveFont( Font.BOLD, 24f );
                    g.setFont( font2 );
                    g.setColor( Color.RED );
                    bounds = g.getFontMetrics().getStringBounds( "GAME OVER", g );
                    x = topLeftX + playingFieldWidth / 2 - (int) (bounds.getWidth()/2);
                    y = topLeftY + 20;
                    g.drawString( "GAME OVER", x, y );

                    if ( controller.getState() == GameState.GAME_OVER )
                    {
                        x = (int) (topLeftX + playingFieldWidth*0.1f);
                        y = (int) (y + 2*bounds.getHeight());

                        final String[] lines = {
                                "ENTER","Restart",
                                "P","Pause",
                                "CURSOR LEFT","Move tile left",
                                "CURSOR RIGHT","Move tile right",
                                "SPACE","Drop tile",
                                "CURSOR UP","Rotate tile CW",
                                "CURSOR DOWN","Rotate tile CCW",
                        };

                        final Font font3 = getFont().deriveFont( Font.BOLD, 18f );
                        g.setFont( font3 );
                        lineHeight = getFontHeight( "TEST", g );
                        for ( int i = 0 ; i < lines.length ; i += 2 ) {
                            final String key = lines[i];
                            final String desc = lines[i+1];
                            g.setColor( Color.WHITE );
                            g.drawString(key, x, y );

                            y += lineHeight + (int) (lineHeight*0.25);
                            bounds = g.getFontMetrics().getStringBounds( key, g );
                            g.setColor( Color.LIGHT_GRAY );
                            g.drawString(" - "+desc, (int) (x+30), y );

                            y += lineHeight + (int) (lineHeight*0.5);
                        }
                    }
                    break;
                default:
            }
            Toolkit.getDefaultToolkit().sync();
        }
    }

    private int getFontHeight(String s,Graphics g) {
        final Rectangle2D bounds = g.getFontMetrics().getStringBounds( s, g );
        return (int) bounds.getHeight();
    }

    public void tick() {
        panel.repaint();
    }

    public IUserInputProvider getInputProvider() {
        return panel.inputProvider;
    }
}