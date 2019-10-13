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

import com.voipfuture.voiptris.api.IGameController;
import com.voipfuture.voiptris.impl.GameScreen;

import javax.swing.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        final Object[] constructorArgs = {10,20}; // width,height
        final IGameController controller = (IGameController)
                Class.forName("com.voipfuture.voiptris.impl.GameController")
                        .getConstructor(Integer.TYPE, Integer.TYPE).newInstance(constructorArgs);

        SwingUtilities.invokeAndWait( () ->
        {
            final GameScreen screen = new GameScreen( controller );
            final Timer timer = new Timer( 16, ev ->
            {
                controller.tick(screen.getInputProvider());
                screen.tick();
            });
            timer.start();
            screen.setVisible( true );
        });
    }
}