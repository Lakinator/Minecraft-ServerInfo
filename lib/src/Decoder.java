import com.sun.istack.internal.Nullable;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 13.05.2017
 * Created by user Schal (Lukas Schalk).
 */

public class Decoder {
    private String toDecode;
    private ScriptEngine engine;

    public Decoder(String toDecode) {
        this.toDecode = toDecode;

        try {
            engine = new ScriptEngineManager().getEngineByName("nashorn");
            engine.eval("function get(rawJSON, key) { return JSON.parse(rawJSON)[key]; }");
            engine.eval("function getArrayValue(rawJSON, key, index) { return JSON.parse(rawJSON)[key][index]; }");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    public String get(String what, boolean isArray, @Nullable String index) {
        Object value = "";

        try {
            if (isArray) {
                value = ((Invocable) engine).invokeFunction("getArrayValue", toDecode, what, index);
            } else {
                value = ((Invocable) engine).invokeFunction("get", toDecode, what);
            }
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return "" + value;
    }

}
