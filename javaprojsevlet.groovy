package com.ecommerce;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private static List<Product> products = new ArrayList<>();
    private static int idCounter = 1;

    public static class Product {
        int id;
        String name;
        String description;
        double price;
        int quantity;

        public Product(int id, String name, String description, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if ("add".equals(action)) {
            out.println("<html><head><title>Add Product</title></head><body>");
            out.println("<h2>Add New Product</h2>");
            out.println("<form action='ProductServlet' method='post'>");
            out.println("<input type='hidden' name='action' value='add'/>");
            out.println("Name: <input type='text' name='name' required/><br/><br/>");
            out.println("Description: <input type='text' name='description' required/><br/><br/>");
            out.println("Price: <input type='number' step='0.01' name='price' required/><br/><br/>");
            out.println("Quantity: <input type='number' name='quantity' required/><br/><br/>");
            out.println("<input type='submit' value='Add Product'/>");
            out.println("</form><br/>");
            out.println("<a href='ProductServlet?action=list'>Back to Product List</a>");
            out.println("</body></html>");
        } else if ("edit".equals(action)) {
            int editId = Integer.parseInt(request.getParameter("id"));
            Product p = getById(editId);
            if (p == null) {
                response.sendRedirect("ProductServlet?action=list");
                return;
            }
            out.println("<html><head><title>Edit Product</title></head><body>");
            out.println("<h2>Edit Product</h2>");
            out.println("<form action='ProductServlet' method='post'>");
            out.println("<input type='hidden' name='action' value='update'/>");
            out.println("<input type='hidden' name='id' value='" + p.id + "'/>");
            out.println("Name: <input type='text' name='name' value='" + p.name + "' required/><br/><br/>");
            out.println("Description: <input type='text' name='description' value='" + p.description + "' required/><br/><br/>");
            out.println("Price: <input type='number' step='0.01' name='price' value='" + p.price + "' required/><br/><br/>");
            out.println("Quantity: <input type='number' name='quantity' value='" + p.quantity + "' required/><br/><br/>");
            out.println("<input type='submit' value='Update Product'/>");
            out.println("</form><br/>");
            out.println("<a href='ProductServlet?action=list'>Back to Product List</a>");
            out.println("</body></html>");
        } else if ("delete".equals(action)) {
            int deleteId = Integer.parseInt(request.getParameter("id"));
            deleteById(deleteId);
            response.sendRedirect("ProductServlet?action=list");
        } else {
            out.println("<html><head><title>Product List</title></head><body>");
            out.println("<h2>Product List</h2>");
            out.println("<a href='ProductServlet?action=add'>Add New Product</a><br/><br/>");
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Quantity</th><th>Actions</th></tr>");
            for (Product p : products) {
                out.println("<tr>");
                out.println("<td>" + p.id + "</td>");
                out.println("<td>" + p.name + "</td>");
                out.println("<td>" + p.description + "</td>");
                out.println("<td>" + p.price + "</td>");
                out.println("<td>" + p.quantity + "</td>");
                out.println("<td>"
                        + "<a href='ProductServlet?action=edit&id=" + p.id + "'>Edit</a> | "
                        + "<a href='ProductServlet?action=delete&id=" + p.id + "' onclick=\"return confirm('Delete this product?');\">Delete</a>"
                        + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            products.add(new Product(idCounter++, name, description, price, quantity));
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Product p = getById(id);
            if (p != null) {
                p.name = name;
                p.description = description;
                p.price = price;
                p.quantity = quantity;
            }
        }

        response.sendRedirect("ProductServlet?action=list");
    }

    private Product getById(int id) {
        for (Product p : products) {
            if (p.id == id) return p;
        }
        return null;
    }

    private void deleteById(int id) {
        products.removeIf(p -> p.id == id);
    }
}
