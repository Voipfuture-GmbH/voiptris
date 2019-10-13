package com.voipfuture.voiptris.impl;

import java.awt.*;

public final class GameShapeConstant {
    protected static final Point[][] GAME_SHAPES = {
            // TYPE_0
            {new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)},

            // TYPE_1
            {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},

            // TYPE_2
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},

            // TYPE_3
            {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},

            // TYPE_4
            {new Point(0, 1), new Point(1, 1), new Point(1,0), new Point(2,0)},

            // TYPE_5
            {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 0)},

            // TYPE_6
            {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
    };
}
