package org.example;

import jakarta.servlet.annotation.WebServlet;

@WebServlet("/multiply")
public class MultiplyServlet extends CalcServlet {
    @Override
    protected int operation(int a, int b) {
        return a * b;
    }
}