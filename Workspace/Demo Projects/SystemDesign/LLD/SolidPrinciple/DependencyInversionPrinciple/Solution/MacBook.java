package com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Solution;

import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.Keyboard;
import com.shoppingService.SystemDesign.LLD.SolidPrinciple.DependencyInversionPrinciple.Utility.Mouse;

// Following DIP
// High-level module uses abstraction
public class MacBook {
    private final Keyboard keyboard;
    private final Mouse mouse;

    // Abstraction - defines contract
    // Dependency injection through constructor
    public MacBook(Mouse mouse, Keyboard keyboard) {
        this.keyboard = keyboard; // Works with any kind of keyboard and mouse
        this.mouse = mouse;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }
}