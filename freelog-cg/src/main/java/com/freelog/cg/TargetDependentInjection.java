package com.freelog.cg;

import java.util.*;
import static java.util.Map.*;

public class TargetDependentInjection {

    public static Map<String, String> javaInjections = Map.ofEntries(
        entry("keep_id", "")  
    );

    public static Map<String, String> javaScriptInjections = Map.ofEntries(
        entry("keep_id", "this._ctx.start._text = this._ctx.getText().substr(1)")  
    );

    public static Map<String, Map<String, String>> injections = Map.ofEntries(
        entry("Java", javaInjections),
        entry("JavaScript", javaScriptInjections)
    );
}