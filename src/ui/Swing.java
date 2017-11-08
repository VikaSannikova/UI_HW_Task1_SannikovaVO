package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Boris on 05.11.2017.
 */

public class Swing extends JFrame {
    public Swing() {
        super("Books");
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int width =1000;
        final int height = 550;
        setSize(width, height);
        final int w = (dim.width-this.getSize().width)/2;
        final int h = (dim.height-this.getSize().height)/2;
        setLocation(w, h);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JTextField bookName = new JTextField(); //создаем текстовые поля
        final JTextField edition = new JTextField();
        final JComboBox genre = new JComboBox();
        genre.addItem(Genre.Classic);
        genre.addItem(Genre.Comics);
        genre.addItem(Genre.Detective);
        genre.addItem(Genre.Fantasy);
        genre.addItem(Genre.Scientific);
        final JTextField qty = new JTextField();
        final JTextField price = new JTextField();
        final JTextField authorName = new JTextField();
        final JTextField email = new JTextField();
        final JComboBox gender = new JComboBox();//это выпадающий список для полов
        gender.addItem(Gender.Man);
        gender.addItem(Gender.Woman);

        final Library m = new Library(); ////////////////////////////////////////////////что такое bookmodel

        final JTable table = new JTable(m); //создаем табличку
        JScrollPane jScrollPane = new JScrollPane(table); //херовина для прогрутки
        add(jScrollPane, BorderLayout.CENTER); //добавить херовину по центру
        JPanel grid = new JPanel(new GridLayout(3, 1, 0, 5));


        final boolean[] isDelete = {false}; //массив делитов
        final boolean[] isChange = {false}; //массив измененных

        table.addMouseListener(new MouseListener() { //херовина которая реагирует на мышку
            @Override
            public void mouseClicked(MouseEvent e) { //событие - клик мышки
                final int row = table.rowAtPoint(e.getPoint());
                if (isDelete[0]) { //если нажата кнопака delete, то мы сожем удалить эту строчку из таблички
                    m.deleteBook(row);
                    isDelete[0] = false;
                }

                if(isChange[0]){
                    Book book = m.getBook(row);
                    Author author = book.getAuthor();

                    final JDialog dialog = new JDialog();
                    dialog.setSize(350, 250);
                    dialog.setLocation(w + width/2 - dialog.getWidth()/2, h+height/2-dialog.getHeight()/2);
                    dialog.setTitle("Change book");
                    dialog.setModal(true);
                    JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

                    bookName.setText(book.getName()); //заполняем исходными данными нашу форму
                    edition.setText(book.getEdition());
                    genre.setSelectedItem(book.getGenre());
                    qty.setText(String.valueOf(book.getQty()));
                    price.setText(String.valueOf(book.getPrice()));
                    authorName.setText(author.getName());
                    email.setText(author.getEmail());
                    gender.setSelectedItem(author.getGender());


                    panel.add(new JLabel("Book name:"));
                    panel.add(bookName);

                    panel.add(new JLabel("Edition"));
                    panel.add(edition);

                    panel.add(new JLabel("Genre"));
                    panel.add(genre);

                    final JLabel qt = new JLabel("Quality:");
                    panel.add(qt);
                    panel.add(qty);

                    final JLabel pri = new JLabel("Price:");
                    panel.add(pri);
                    panel.add(price);

                    panel.add(new JLabel("Author's name:"));
                    panel.add(authorName);

                    panel.add(new JLabel("Author's email:"));
                    panel.add(email);

                    panel.add(new JLabel("Author's gender:"));
                    panel.add(gender);

                    dialog.add(panel);

                    JButton ok = new JButton("OK");
                    ok.addActionListener(new ActionListener() { //что будет если нажать кнопку ОК
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            boolean flag = true;
                            int q = 0;
                            double pr =0;

                            if((bookName.getText().isEmpty())||(edition.getText().isEmpty())||(authorName.getText().isEmpty())||(email.getText().isEmpty())){
                                JDialog error = new JDialog(); //если одно из стринговых полей пустое, то ошибка
                                error.setSize(175, 150);
                                error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                                error.add(new JLabel("Empry fields!"));
                                error.setModal(true);
                                error.setVisible(true);
                                flag = false;
                            }
                                q = Integer.valueOf(qty.getText());
                                pr = Double.valueOf(price.getText());
                                pri.setForeground(Color.BLACK);
                                price.setBackground(Color.WHITE);
                                qt.setForeground(Color.BLACK);
                                qty.setBackground(Color.WHITE);
                                if ((q<0)||(pr<0)){
                                    JDialog error = new JDialog();
                                    if(pr<0) {
                                        pri.setForeground(Color.RED);
                                        price.setBackground(Color.RED);
                                        if(q>=0){
                                            qt.setForeground(Color.BLACK);
                                            qty.setBackground(Color.WHITE);
                                        }
                                    }
                                    if(q<0) {
                                        qt.setForeground(Color.RED);
                                        qty.setBackground(Color.RED);
                                        if(pr>=0){
                                            pri.setForeground(Color.BLACK);
                                            price.setBackground(Color.WHITE);
                                        }

                                    }
                                    error.setSize(175, 150);
                                    error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                                    error.add(new JLabel("Negative values!"));
                                    error.setModal(true);
                                    error.setVisible(true);
                                    flag = false;
                                }


                            if(flag) { //если все топ, то отправляем в наш bookmodel
                                m.setBook(row,new Book(bookName.getText(),edition.getText(),(Genre) genre.getSelectedItem(), new Author(authorName.getText(), email.getText(), (Gender)gender.getSelectedItem()), pr, q));
                                dialog.dispose();
                            }
                        }
                    });

                    dialog.add(ok,BorderLayout.SOUTH); //располагаем кнопку на ЮГ
                    dialog.setVisible(true); //отрисовываем

                    isChange[0] = false; //кнопка change разжимается
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        final JButton create = new JButton("Create"); create.setEnabled(false); //создаем кнопки
        final JButton delete = new JButton("Delete"); delete.setEnabled(false);
        final JButton change = new JButton("Change");change.setEnabled(false);





        delete.addActionListener(new ActionListener() { //установление события на кнопку delete
            @Override
            public void actionPerformed(ActionEvent e) {
                isDelete[0] = true;
            }
        });

        change.addActionListener(new ActionListener() { //установление события на кнопку change
            @Override
            public void actionPerformed(ActionEvent e) {
                isChange[0] = true;
            }
        });

        create.addActionListener(new ActionListener() { //установление события на кнопку create
            @Override
            public void actionPerformed(ActionEvent e) {
                final JDialog dialog = new JDialog(); //тут окошко в такой фигней
                dialog.setSize(350, 250);
                dialog.setLocation(w + width/2 - dialog.getWidth()/2, h+height/2-dialog.getHeight()/2);
                dialog.setTitle("New book");
                dialog.setModal(true);
                JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));


                panel.add(new JLabel("Book name:"));
                panel.add(bookName);

                panel.add(new JLabel("Edition"));
                panel.add(edition);

                panel.add(new JLabel("Genre"));
                panel.add(genre);

                final JLabel qt = new JLabel("Quality:");
                panel.add(qt);
                panel.add(qty);

                final JLabel pri = new JLabel("Price:");
                panel.add(pri);
                panel.add(price);

                panel.add(new JLabel("Author name:"));
                panel.add(authorName);

                panel.add(new JLabel("E-mail:"));
                panel.add(email);

                panel.add(new JLabel("Gender:"));
                panel.add(gender);

                dialog.add(panel); //добавляем все в окошко

                JButton ok = new JButton("OK");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean flag = true;
                        int q = 0;
                        double pr =0;


                        if((bookName.getText().isEmpty())||(edition.getText().isEmpty())||(authorName.getText().isEmpty())||(email.getText().isEmpty())){
                            JDialog error = new JDialog();//если пустые стринги
                            error.setSize(175, 150);
                            error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                            error.add(new JLabel("  Remained Empty Field!"));
                            error.setModal(true);
                            error.setVisible(true);
                            flag = false;
                        }
                            q = Integer.valueOf(qty.getText());
                            pr = Double.valueOf(price.getText());
                            pri.setForeground(Color.BLACK);
                            price.setBackground(Color.WHITE);
                            qt.setForeground(Color.BLACK);
                            qty.setBackground(Color.WHITE);

                            if ((q<0)||(pr<0)) {
                                JDialog error = new JDialog();
                                if(pr<0) {
                                    pri.setForeground(Color.RED);
                                    price.setBackground(Color.RED);
                                    if(q>=0){
                                        qt.setForeground(Color.BLACK);
                                        qty.setBackground(Color.WHITE);
                                    }
                                }
                                if(q<0) {
                                    qt.setForeground(Color.RED);
                                    qty.setBackground(Color.RED);
                                    if(pr>=0){
                                        pri.setForeground(Color.BLACK);
                                        price.setBackground(Color.WHITE);
                                    }

                                }
                                error.setSize(175, 150);
                                error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                                error.add(new JLabel("   Incorrect Number Format!"));
                                error.setModal(true);
                                error.setVisible(true);
                                flag = false;
                            }



                        if(flag) { //все топ, грузим в форму
                            m.addBook(new Book(bookName.getText(), edition.getText(), (Genre) genre.getSelectedItem(),new Author(authorName.getText(), email.getText(), (Gender)gender.getSelectedItem()), pr, q));
                            dialog.dispose();
                        }
                    }
                });

                dialog.add(ok,BorderLayout.SOUTH);
                dialog.setVisible(true);//отрисовываем
            }
        });

        grid.add(create); //добавляем в грид кнопочки
        grid.add(change);
        grid.add(delete);






        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER)); //что такое jpalel
        flow.add(grid);
        add(flow, BorderLayout.EAST);

        JMenuBar menu = new JMenuBar(); //создаем меню сверху
        JMenu file = new JMenu("File"); //делаем вырадающий список
        JMenuItem newFile = new JMenuItem("New"); //создаем вкладку NEW
        file.add(newFile); //добавляем NEW в FILE
        newFile.addActionListener(new ActionListener() { //что происходит при вызове кнопки
            @Override
            public void actionPerformed(ActionEvent e) {
                final JDialog dialog = new JDialog(); //создаем диалоговое окно
                dialog.setSize(350, 150);
                dialog.setLocation(w + width/2 - dialog.getWidth()/2, h+height/2-dialog.getHeight()/2);
                dialog.setTitle("New library:"); //называем его так

                dialog.setModal(true); ///////////////////////////////////////////////////////////////////////это я хз для чего

                JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5)); //заолняем его данными
                panel.add(new JLabel("File name:"));  //вот там такое название
                final JTextField text = new JTextField(); //поле
                panel.add(text);
                JButton ok = new JButton("OK"); //кнопка
                dialog.add(panel); //добавляем на окно нашу панель
                dialog.add(ok,BorderLayout.SOUTH); //располагаем кнопку ОК на Юг


                ok.addActionListener(new ActionListener() { //что будет при действии с кнопкой
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!text.getText().isEmpty()) { //если мы что-то ввели
                            boolean flag = true;
                            try {
                                m.newFile(text.getText()); //созаем новый файл с книгами

                                dialog.dispose();/////////////////////////////////////////////хз для чего это

                            } catch (IOException ex){ //если у нас не создался файл, то мы выводим об этом уведдомление
                                JDialog error = new JDialog();
                                error.setSize(175, 150);
                                error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                                error.add(new JLabel("File is not created!"), BorderLayout.CENTER);
                                error.setModal(true);
                                error.setVisible(true);
                                flag = false;
                            }
                            if (flag){ //если все ок, то активируем кнопки
                                create.setEnabled(true);
                                delete.setEnabled(true);
                                change.setEnabled(true);
                                m.setFile(text.getText()+".txt"); //создаем файл в корневом диалоге формата txt
                            }
                        }
                    }
                });
                dialog.setVisible(true); //наше диалоговое окно теперь видно
            }
        });

        JMenuItem open = new JMenuItem("Open"); //создаем OPEN
        file.add(open); //добавляем в выпадающее меню OPEN

        open.addActionListener(new ActionListener() { //действия с OPEN
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                m.clear(); ///////////////////////////////////////////////////////////////////очищаем
                JFileChooser openFile = new JFileChooser(); //выбираем файл который хотим открыть
                openFile.setCurrentDirectory(new File(".")); // короче эта поебень мне не нужна, сложно.
                int res = openFile.showDialog(null, "Open file");
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = openFile.getSelectedFile();
                    try {
                        m.setFile(file.getName());
                    } catch (NumberFormatException ex){
                        JDialog error = new JDialog();
                        error.setSize(175, 150);
                        error.setLocation(w + width/2 - error.getWidth()/2, h+height/2-error.getHeight()/2);
                        error.add(new JLabel(" Incorrect Selected File!"));
                        error.setModal(true);
                        error.setVisible(true);
                        flag = false;
                    }
                    if (flag) {
                        create.setEnabled(true);
                        delete.setEnabled(true);
                        change.setEnabled(true);
                    }
                    setVisible(true);
                }
            }
        });
        JMenuItem save = new JMenuItem("Save"); //сохраняем наш файлик
        file.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m.saveFile(); //збс сохранили
            }
        });

        menu.add(file); //добавляем в меню file
        setJMenuBar(menu);

        setVisible(true); //и рисуем все это

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Swing();
            }
        });
    }
}