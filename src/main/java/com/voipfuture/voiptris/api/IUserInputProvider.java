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

public interface IUserInputProvider
{
    /**
     * Must be called on every game tick to read
     * the user's current input (if any).
     * @return user input or <code>Optional.empty()</code>
     */
    Optional<UserInput> tick();

    /**
     * Flushes any input events that may still be pending.
     *
     * Users may provide multiple inputs (key presses)
     * in quick succession, these
     * are buffered and returned one-by-one on each
     * {@link #tick()} invocation.
     *
     */
    void clearInput();
}