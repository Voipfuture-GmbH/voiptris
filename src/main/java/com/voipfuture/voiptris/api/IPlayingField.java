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
package com.voipfuture.voiptris.api;

import java.util.Optional;

/**
 * Tetris playing field.
 *
 * The playing field is organized as a grid with
 * cartesian coordinates and X=0/Y=0 being the
 * top-left corner.
 * Each non-empty grid cell contains the {@link TileType} of
 * the tile that occupies it.
 */
public interface IPlayingField
{
    /**
     * Returns the width of this playing field in grid cells.
     * @return
     */
    int width();

    /**
     * Returns the height of this playing field in grid cells.
     * @return
     */
    int height();

    /**
     * Returns the type of tile that is
     * occupying a given grid location.
     *
     * @param x X coordinate (0 is left-most column)
     * @param y Y coordinate (0 is top-most row)
     * @return
     */
    Optional<TileType> getTileType(int x, int y);
}