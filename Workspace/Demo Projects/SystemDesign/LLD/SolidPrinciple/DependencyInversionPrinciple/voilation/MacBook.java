package com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.voilation;


import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.Keyboard;
import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.Mouse;
import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.WiredKeyboard;
import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.WiredMouse;

// VIOLATION OF DIP
// High-level module directly depending on low-level module
public class MacBook {
    private final WiredKeyboard keyboard;
    private final WiredMouse mouse;

    // Direct dependency on concrete class
    public MacBook(WiredKeyboard wiredKeyboard, WiredMouse wiredMouse) {
        keyboard = wiredKeyboard; // Tight coupling
        mouse = wiredMouse; // Tight coupling
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }
}