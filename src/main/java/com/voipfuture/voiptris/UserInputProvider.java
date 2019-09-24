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

import com.voipfuture.voiptris.api.IUserInputProvider;
import com.voipfuture.voiptris.api.UserInput;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserInputProvider implements IUserInputProvider
{
    private int ticksPerRepetition = 10;

    private final List<KeyPress> activeKeys = new ArrayList<>();
    private final List<UserInput> inputBuffer = new ArrayList<>();

    private final KeyAdapter adapter = new KeyAdapter()
    {
        @Override public void keyPressed(KeyEvent e) { UserInputProvider.this.keyPressed( e ); }
        @Override public void keyReleased(KeyEvent e) { UserInputProvider.this.keyReleased( e ); }
    };

    private final class KeyPress
    {
        public final UserInput userInput;
        public int ticksUntilRepetition;

        private KeyPress(UserInput input)
        {
            this.userInput = input;
            this.ticksUntilRepetition = UserInputProvider.this.ticksPerRepetition;
        }

        @Override
        public boolean equals(Object obj)
        {
            if ( obj instanceof KeyPress) {
                return ((KeyPress) obj).userInput == this.userInput;
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            return userInput.hashCode();
        }

        public void tick(List<UserInput> buffer)
        {
            if (--ticksUntilRepetition==0)
            {
                this.ticksUntilRepetition = UserInputProvider.this.ticksPerRepetition;
                buffer.add(this.userInput);
            }
        }
    }

    private boolean isNotPressed(UserInput input)
    {
        for ( var x : activeKeys) {
            if ( x.userInput == input ) {
                return false;
            }
        }
        return true;
    }

    private void keyPressed(KeyEvent event)
    {
        mapKeyToInput( event ).ifPresent( input ->
        {
            if ( isNotPressed( input ) )
            {
                final KeyPress keyPress = new KeyPress( input );
                activeKeys.add( keyPress );
                inputBuffer.add( input );
            }
        });
    }

    private void keyReleased(KeyEvent event)
    {
        mapKeyToInput( event ).ifPresent( input -> {
            for (int i = 0, len = activeKeys.size(); i < len; i++)
            {
                final KeyPress x = activeKeys.get( i );
                if ( x.userInput == input )
                {
                    activeKeys.remove(i);
                    break;
                }
            }
        });
    }

    private Optional<UserInput> mapKeyToInput(KeyEvent event)
    {
        UserInput result = null;
        switch( event.getKeyCode() )
        {
            case KeyEvent.VK_P:
                result = UserInput.PAUSE; break;
            case KeyEvent.VK_LEFT:
                result = UserInput.LEFT; break;
            case KeyEvent.VK_RIGHT:
                result = UserInput.RIGHT; break;
            case KeyEvent.VK_UP:
                result = UserInput.ROTATE_CW; break;
            case KeyEvent.VK_DOWN:
                result = UserInput.ROTATE_CCW; break;
            case KeyEvent.VK_ENTER:
                result = UserInput.RESTART; break;
            case KeyEvent.VK_SPACE:
                result = UserInput.HARD_DROP; break;
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserInput> tick()
    {
        for (KeyPress keyPress : activeKeys)
        {
            keyPress.tick( inputBuffer );
        }
        if ( inputBuffer.isEmpty() ) {
            return Optional.empty();
        }
        return Optional.of( inputBuffer.remove(0) );
    }

    public void attach(Component component)
    {
        component.addKeyListener( adapter );
    }

    @Override
    public void clearInput()
    {
        activeKeys.clear();
        inputBuffer.clear();
    }
}