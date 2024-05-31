package org.example;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/add")
public class AddServlet extends CalcServlet {
    @Override
    protected int operation(int a, int b) {
        return a + b;
    }
}