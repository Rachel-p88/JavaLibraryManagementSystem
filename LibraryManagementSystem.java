import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LibraryManagementSystem {

    private static final ArrayList<Book> books = new ArrayList<>();
    private static final JTextArea textArea = new JTextArea();
    private static final JTextField titleField = new JTextField(), authorField = new JTextField(), issueField = new JTextField(), returnField = new JTextField();

    private static final Color FRAME_COLOR = new Color(187, 222, 251); // Pastel blue
    private static final Color PANEL_COLOR = new Color(224, 242, 241); // Greenish blue
    private static final Color BUTTON_COLOR = new Color(78, 140, 161); // Teal-ish color
    private static final Color BUTTON_HOVER_COLOR = new Color(211, 47, 47); // Hint of red for hover
    private static final Color TEXT_COLOR = new Color(60, 60, 60); // Dark gray text
    private static final Color BORDER_COLOR = new Color(200, 200, 200); // Light gray borders

    public static void main(String[] args) {
        LoginPage.showLoginPage(); // Call the LoginPage class
    }

    public static void startApp() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) { e.printStackTrace(); }

        initializeBooks(); // Call preloaded books

        JFrame frame = createFrame();
        JPanel panel = createInputPanel();
        frame.add(panel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.SOUTH);
        addActionListeners(panel);
        frame.setVisible(true);
    }

    private static void initializeBooks() {
        books.add(new Book("To Kill a Mockingbird", "Harper Lee"));
        books.add(new Book("1984", "George Orwell"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book("Pride and Prejudice", "Jane Austen"));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger"));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien"));
        books.add(new Book("Rich Dad Poor Dad", "Robert T. Kiyosaki"));
        books.add(new Book("War and Peace", "Leo Tolstoy"));
        books.add(new Book("Crime and Punishment", "Fyodor Dostoevsky"));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien"));
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(FRAME_COLOR);
        return frame;
    }

    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createLabel("Book Title:"));
        panel.add(titleField);
        panel.add(createLabel("Book Author:"));
        panel.add(authorField);
        panel.add(createLabel("Issue Book Title:"));
        panel.add(issueField);
        panel.add(createLabel("Return Book Title:"));
        panel.add(returnField);

        panel.add(createStyledButton("Add Book"));
        panel.add(createStyledButton("Remove Book"));
        panel.add(createStyledButton("Issue Book"));
        panel.add(createStyledButton("Return Book"));
        panel.add(createStyledButton("Display Books"));
        panel.add(createStyledButton("Number of Books"));

        return panel;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText("Click to " + text);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private static void addActionListeners(JPanel panel) {
        JButton addButton = (JButton) panel.getComponent(8);
        JButton removeButton = (JButton) panel.getComponent(9);
        JButton issueButton = (JButton) panel.getComponent(10);
        JButton returnButton = (JButton) panel.getComponent(11);
        JButton displayButton = (JButton) panel.getComponent(12);
        JButton countButton = (JButton) panel.getComponent(13);

        addButton.addActionListener(e -> {
            String title = titleField.getText(), author = authorField.getText();
            if (!title.isEmpty() && !author.isEmpty()) {
                addBook(title, author);
                clearFields();
            } else {
                showError("Please enter both title and author.");
            }
        });

        removeButton.addActionListener(e -> {
            String title = titleField.getText(), author = authorField.getText();
            if (!title.isEmpty() && !author.isEmpty()) {
                removeBook(title, author);
                clearFields();
            } else {
                showError("Please enter both title and author to remove.");
            }
        });

        issueButton.addActionListener(e -> {
            String title = issueField.getText();
            if (!title.isEmpty()) {
                issueBook(title);
                issueField.setText("");
            } else {
                showError("Please enter a title to issue.");
            }
        });

        returnButton.addActionListener(e -> {
            String title = returnField.getText();
            if (!title.isEmpty()) {
                returnBook(title);
                returnField.setText("");
            } else {
                showError("Please enter a title to return.");
            }
        });

        displayButton.addActionListener(e -> displayBooks());
        countButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Books: " + books.size()));
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void addBook(String title, String author) {
        books.add(new Book(title, author));
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    }

    private static void removeBook(String title, String author) {
        boolean removed = books.removeIf(book ->
            book.title.equalsIgnoreCase(title) && book.author.equalsIgnoreCase(author)
        );

        if (removed) {
            JOptionPane.showMessageDialog(null, "Book removed successfully.");
        } else {
            showError("Book not found.");
        }
    }

    private static void issueBook(String title) {
        books.stream().filter(book -> book.title.equalsIgnoreCase(title) && book.isAvailable).findFirst().ifPresentOrElse(
            book -> {
                book.isAvailable = false;
                JOptionPane.showMessageDialog(null, "Book issued.");
            },
            () -> showError("Book not available.")
        );
    }

    private static void returnBook(String title) {
        books.stream().filter(book -> book.title.equalsIgnoreCase(title) && !book.isAvailable).findFirst().ifPresentOrElse(
            book -> {
                book.isAvailable = true;
                JOptionPane.showMessageDialog(null, "Book returned.");
            },
            () -> showError("This book was not issued.")
        );
    }

    private static void displayBooks() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Books In The Library");
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);

        JPanel bookPanel = new JPanel();
        bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (books.isEmpty()) {
            JLabel noBooksLabel = new JLabel("No books available.");
            noBooksLabel.setFont(new Font("Arial", Font.BOLD, 14));
            noBooksLabel.setForeground(TEXT_COLOR);
            noBooksLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bookPanel.add(noBooksLabel);
        } else {
            for (Book book : books) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(240, 248, 255));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                JLabel bookInfo = new JLabel("<html><b>Title:</b> " + book.title + "<br><b>Author:</b> " + book.author + "<br><b>Status:</b> " + (book.isAvailable ? "Available" : "Issued") + "</html>");
                bookInfo.setFont(new Font("Arial", Font.PLAIN, 13));
                bookInfo.setForeground(TEXT_COLOR);

                card.add(bookInfo, BorderLayout.CENTER);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
                bookPanel.add(card);
                bookPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(bookPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private static void clearFields() {
        titleField.setText("");
        authorField.setText("");
        issueField.setText("");
        returnField.setText("");
    }
}
