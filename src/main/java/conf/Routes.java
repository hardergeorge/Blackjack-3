/**
 * Copyright (C) 2012 the original author or authors.
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

package conf;


import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController.class, "index");
        //router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
        ///////////////////////////////////////////////////////////////////////
        // Blackjack Routes
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/BlackjackStart").with(ApplicationController.class, "start");
        router.GET().route("/Blackjack").with(ApplicationController.class, "Blackjack");
        router.GET().route("/game").with(ApplicationController.class, "gameGet");

        router.POST().route("/hit").with(ApplicationController.class, "hitPost");
        router.POST().route("/doubleDown").with(ApplicationController.class, "doubleDownPost");
        router.POST().route("/split").with(ApplicationController.class, "splitPost");
        router.POST().route("/stay").with(ApplicationController.class, "stayPost");
        router.POST().route("/newhand").with(ApplicationController.class, "newHandPost");
 


        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        //router.GET().route("/.*").with(ApplicationController.class, "index");


    }

}
