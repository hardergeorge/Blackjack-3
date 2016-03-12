/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import models.Game;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;


@Singleton
public class ApplicationController {

    public Result index() {

        return Results.html();

    }

    public Result start() {
        return Results.html().template("views/Blackjack/BlackjackStartScreen.html");
    }

    public Result Blackjack() {
        return Results.html().template("views/Blackjack/Blackjack.html");
    }

    public Result gameGet () {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();
        g.dealHand();

        return Results.json().render(g);
    }

    public Result hitPost (Context context, Game g) {
        if (context.getRequestPath().contains("hit")) {
            g.dealOne(g.dealTo);
        }
        return Results.json().render(g);
    }

    public Result doubleDownPost (Context context, Game g) {
        if (context.getRequestPath().contains("doubleDown")) {
            g.doubleDown();
        }
        return Results.json().render(g);
    }

    public Result splitPost (Context context, Game g) {
        if (context.getRequestPath().contains("split")) {
            g.split();
        }
        return Results.json().render(g);
    }

    public Result stayPost (Context context, Game g) {
        if (context.getRequestPath().contains("stay")) {
            if (g.hasSplit && g.dealTo < 2) {
                g.updateDealTo();
            }
            else {
                g.dealerPlay();
                if(g.noWinner()) {
                    g.winner();
                }
            }
        }
        return Results.json().render(g);
    }

    public Result newHandPost (Context context, Game g) {
        if (context.getRequestPath().contains("newhand")) {
            g.resetGame();
        }
        return Results.json().render(g);
    }


    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
    public static class SimplePojo {

        public String content;
        
    }
}
