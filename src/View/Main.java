/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import AppPackage.AnimationClass;
import DAO.ChuyenDeDAO;
import DAO.HocVienDAO;
import DAO.KhoaHocDAO;
import DAO.NguoiHocDAO;
import DAO.NhanVienDAO;
import DAO.ThongKeDAO;
import DAO.jdbcDao;
import Helper.DialogHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ChuyenDeD;
import model.HocVienH;
import model.KhoaHocK;
import model.NguoiHocN;
import model.NhanVienV;

/**
 *
 * @author exfic
 */
public class Main extends javax.swing.JFrame {

    NhanVienDAO daoNV = new NhanVienDAO();
    ChuyenDeDAO daoCD = new ChuyenDeDAO();
    KhoaHocDAO daoKH = new KhoaHocDAO();
    NguoiHocDAO daoN_H = new NguoiHocDAO();
    HocVienDAO daoHV = new HocVienDAO();
    ThongKeDAO daoTK = new ThongKeDAO();

    ButtonGroup groupNV;
    ButtonGroup groupHV;
    JFileChooser fileChooser = new JFileChooser();

    int indexNguoiHoc = 0;   // id next table
    int indexNhanVien = 0;
    int indexKhoaHoc = 0;
    int indexHocVien = 0;
    int indexCD = 0;
    private String mk;
    private String TenNgDung;
    private String IdLogin;  // id ngdung login update password
    private String IdNV;      // id Nhan vien update
    private String IdCD;      // id chuyen de update
    private int IdKH;      // id KHoa hoc update
    private int IdHV;      // id DuAn update
    private int IdSP;      // id sanPham update
    private int maKH_O_HV; // open form hoc vien
    private boolean role;
    private int x = 0;
    private Timer tm;
    String[] list = {
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/sl1.jpg",
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/sl2.png",
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/sl3.png",
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/sl4.jpg",
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/sl5.png",
        "E:/Users/NetBeansProjects/ThamKhao_Swing/Quan_Li_BDS_Form_2/src/imager/hinh/logoLogin.png",};

    public Main() {
        initComponents();

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // k cho chương trình chạy ngầm
        setResizable(false); // có thể thay đổi kích thước frame hay không, true có, false không
        this.lbl();
        this.lblHiden();
        this.time();
        this.JPaneSetOn();
        this.TextLogin();
        this.slide();
        this.group();
        txtUserName.setText("TeoNV");
        txtPass.setText("123456");
    }
    // in excel 

//    Excel excel = new Excel();
//    List<DuAn> projectList = new ArrayList<>();
//
//    public void printIntoExcel() throws IOException {
//        excel.setProjects(this.projectList);
//        excel.writeProjectToFile();
//    }
    void time() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date now = new Date(); //1
                SimpleDateFormat formater = new SimpleDateFormat(); //2
                formater.applyPattern("hh : mm : ss aa"); // 2
                lblTime.setText(formater.format(now));
            }

        }).start();
    }

    void slide() {
        slide.setBounds(24, 72, 758, 390);
        setImageSize(5);

        tm = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setImageSize(x);
                x += 1;
                if (x >= list.length) {
                    x = 0;
                }
            }
        });

        tm.start();
        //  setLayout(null);
    }
// set size slide

    public void setImageSize(int i) {
        ImageIcon icon = new ImageIcon(list[i]);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(slide.getWidth(), slide.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        slide.setIcon(newImc);
    }

    public static boolean saveLogo(File file) {
        File dir = new File("logos");
        // Tạo thư mục nếu chưa tồn tại
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, file.getName());
        try {
            // Copy vào thư mục logos (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static ImageIcon readLogo(JLabel lbl, String fileName) {
        // đọc file ảnh
        File path = new File("logos", fileName);
        // setsize anh
        ImageIcon icon = new ImageIcon(path.getAbsolutePath());
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        //  return new ImageIcon(newImg);
        return newImc;
    }

    public void setAnhSize(JLabel lbl) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
//            String filename = file.getName();
//            ImageIcon icon = new ImageIcon(filename);
//            Image img = icon.getImage();
//            Image newImg = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
//            ImageIcon newImc = new ImageIcon(newImg);
            if (saveLogo(file)) {
                // Hiển thị hình lên form
                lbl.setIcon(readLogo(lbl, file.getName()));
                lbl.setToolTipText(file.getName());
            }

        }

    }

    void group() {
        groupHV = new ButtonGroup();
        groupHV.add(rdoTatCa);
        groupHV.add(rdoChuaNhap);
        groupHV.add(rdoDaNhap);
        rdoTatCa.setSelected(true);

        groupNV = new ButtonGroup();
        groupNV.add(rdoTruongPhong);
        groupNV.add(rdoNhanVien);

    }

    void JPaneSetOn() {
        jPaneExit.setVisible(false);
        JPaneThoat.setVisible(false);
        JPaneLoogin.setVisible(false);
        JPaneLogout.setVisible(false);
        JPaneMenuLogin2.setVisible(false);
        JPaneHeThong.setVisible(false);
        JPaneQuanLi.setVisible(false);
        JPaneThongKe.setVisible(false);
        JPanePhanTich.setVisible(false);
        JPaneTaiKhoan.setVisible(false);
        JPaneGioiThieu.setVisible(false);
        JPaneAbout.setVisible(false);

        jTabbQLKhoaHoc.setVisible(false);
        jTabbQLNhanVien.setVisible(false);
        jTabbQLHocVien.setVisible(false);
        jTabbQLChuyenDe.setVisible(false);

    }

    void lbl() {
        lblTime.setIcon(new ImageIcon("E:\\Users\\NetBeansProjects\\ThamKhao_Swing\\Quan_Li_BDS_Form_2\\src\\imager\\Alarm.png"));
        lblGif.setIcon(new ImageIcon("E:\\Users\\NetBeansProjects\\ThamKhao_Swing\\Quan_Li_BDS_Form_2\\src\\imager\\icon\\a.gif"));
        lblGach.setSize(0, 0);
        lblBackG.setVisible(false);
        lblGach.setVisible(false);
        lblGif.setVisible(false);
        lblQLhocVien.setVisible(false);
        lblQLnhanVien.setVisible(false);
        lblQLkhoaHoc.setVisible(false);
        lblQLchuyenDe.setVisible(false);

    }

    void lblHiden() {
        lblEye.setVisible(false);
        lblHide1.setVisible(false);
        lblHide2.setVisible(false);
        lblHide3.setVisible(false);
        lblHideLogout.setVisible(false);
        lbl_BackG_Home.setVisible(false);
    }

    void TextLogin() {
        if (txtUserName.getText().isEmpty()) {
            txtUserName.setForeground(Color.GRAY);
            txtUserName.setText("Nhập tên đăng nhập");
        }

        if (txtPass.getText().isEmpty()) {
            txtPass.setForeground(Color.GRAY);
            txtPass.setText("Nhập mật khẩu");
            txtPass.setEchoChar((char) 0);   // chuyển mật khẩu sang dạng String
        }

        if (txtUserName1.getText().isEmpty()) {
            txtUserName1.setForeground(Color.GRAY);
            txtUserName1.setText("Nhập tên đăng nhập");
        }

        if (txtPass1.getText().isEmpty()) {
            txtPass1.setForeground(Color.GRAY);
            txtPass1.setText("Nhập mật khẩu");
            txtPass1.setEchoChar((char) 0);
        }

        if (txtPassCu.getText().isEmpty()) {
            txtPassCu.setForeground(Color.GRAY);
            txtPassCu.setText("Nhập mật khẩu cũ");
            txtPassCu.setEchoChar((char) 0);
        }

        if (txtPassMoi.getText().isEmpty()) {
            txtPassMoi.setForeground(Color.GRAY);
            txtPassMoi.setText("Nhập mật khẩu mới");
            txtPassMoi.setEchoChar((char) 0);
        }

        if (txtPassXN.getText().isEmpty()) {
            txtPassXN.setForeground(Color.GRAY);
            txtPassXN.setText("Xác nhận mật khẩu mới");
            txtPassXN.setEchoChar((char) 0);
        }
    }

    void ExitPane() {

        jPaneExit.setSize(1120, 590);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        AnimationClass anim = new AnimationClass();

        anim.jLabelXLeft(left.getX(), -300, 2, 2, left);
        anim.jLabelXRight(right.getX(), 750, 2, 2, right);
        anim.jLabelYUp(top.getY(), -70, 4, 1, top);
        anim.jLabelYDown(bot.getY(), 400, 3, 1, bot);
    }

    void ThoatPane() {

        JPaneThoat.setSize(1120, 590);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        AnimationClass anim = new AnimationClass();

        anim.jLabelXLeft(left1.getX(), -300, 2, 2, left1);
        anim.jLabelXRight(right1.getX(), 750, 2, 2, right1);
        anim.jLabelYUp(top1.getY(), -70, 4, 1, top1);
        anim.jLabelYDown(bot1.getY(), 400, 3, 1, bot1);
    }

    void MenuThu() {
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    for (int a = 0; a <= 20; a++) {
                        Thread.sleep(30);
                        if (a == 1) {
                            jPaneMenu.setSize(154, 590);
                        }
                        if (a == 2) {
                            jPaneMenu.setSize(148, 590);
                        }
                        if (a == 3) {
                            jPaneMenu.setSize(142, 590);
                        }
                        if (a == 4) {
                            jPaneMenu.setSize(136, 590);
                        }
                        if (a == 5) {
                            jPaneMenu.setSize(130, 590);
                        }
                        if (a == 6) {
                            jPaneMenu.setSize(124, 590);
                        }
                        if (a == 7) {
                            jPaneMenu.setSize(118, 590);
                        }
                        if (a == 8) {
                            jPaneMenu.setSize(112, 590);
                        }
                        if (a == 9) {
                            jPaneMenu.setSize(106, 590);
                        }
                        if (a == 10) {
                            jPaneMenu.setSize(100, 590);
                        }
                        if (a == 11) {
                            jPaneMenu.setSize(94, 590);
                        }
                        if (a == 12) {
                            jPaneMenu.setSize(88, 590);
                        }
                        if (a == 13) {
                            jPaneMenu.setSize(82, 590);
                        }
                        if (a == 14) {
                            jPaneMenu.setSize(76, 590);
                        }
                        if (a == 15) {
                            jPaneMenu.setSize(70, 590);
                        }
                        if (a == 16) {
                            jPaneMenu.setSize(64, 590);
                        }
                        if (a == 17) {
                            jPaneMenu.setSize(58, 590);
                        }
                        if (a == 18) {
                            jPaneMenu.setSize(52, 590);
                        }
                        if (a == 19) {
                            jPaneMenu.setSize(46, 590);
                        }

                        if (a == 20) {
                            jPaneMenu.setSize(40, 590);
                            lblGach.setSize(40, 50);
                            lblGif.setSize(0, 0);
                            lblBackG.setVisible(true);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        th.start();
    }

    // set get ngay thang
    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("MM/dd/yyyy");

    public static String ToString(Date date, String... pattern) {
        if (pattern.length > 0) {
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        return DATE_FORMATER.format(date);
    }

    public static Date toDate(String date, String... pattern) {
        try {
            if (pattern.length > 0) {
                DATE_FORMATER.applyPattern(pattern[0]);
            }
            return DATE_FORMATER.parse(date);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    // login
    void UserLogin() {
        String manv = txtUserName.getText();
        String pass = new String(txtPass.getPassword());

        try {
            NhanVienV model = daoNV.findByIdNV(manv);

            if (model != null) {
                mk = model.getMatKhau();
                IdLogin = model.getMaNV();
                TenNgDung = model.getHoTen();
                if (mk.equals(pass)) {
                    role = model.getVaiTro();
                    String nameRole = "";
                    if (role == true) {
                        nameRole = " Quản Lí";
                    } else {
                        nameRole = " Nhân viên";
                    }
                    JOptionPane.showMessageDialog(this, " Bạn đã đăng nhập thành công với User : " + manv + " " + "(" + nameRole + " )");
                    JPaneMenuLogin.setVisible(false);
                    JPaneMenuLogin2.setVisible(true);
                    // JPaneLoogin.setVisible(false);
                    JPaneHome.setVisible(true);
                    if (JPaneLoogin.isVisible() || JPaneLogout.isVisible() || jPaneExit.isVisible() || jPaneExit.isVisible() || JPaneHeThong.isVisible() || JPaneQuanLi.isVisible() || JPaneThongKe.isVisible() || JPanePhanTich.isVisible()) {
                        jPaneExit.setVisible(false);
                        JPaneThoat.setVisible(false);
                        JPaneLoogin.setVisible(false);
                        JPaneLogout.setVisible(false);
                        JPaneHeThong.setVisible(false);
                        JPaneQuanLi.setVisible(false);
                        JPaneThongKe.setVisible(false);
                        JPanePhanTich.setVisible(false);
                    }
                    if (!btnHome.isEnabled() || !btnHeThong.isEnabled() || !btnThongKe.isEnabled() || !btnPhanTich.isEnabled() || !btnQuanLi.isEnabled()) {
                        btnHome.setEnabled(true);
                        btnPhanTich.setEnabled(true);
                        btnThongKe.setEnabled(true);
                        btnQuanLi.setEnabled(true);
                        btnHeThong.setEnabled(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Sai password, vui lòng thử lại !");
                }
            } else {
                JOptionPane.showMessageDialog(this, " Sai tên đăng nhập, vui lòng thử lại !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Nhan vien
    public boolean checkNV() {

        if (txtNV_ID.getText().equals("") || txtNVhoTen.getText().equals("") || txtNVmatKhau.getText().equals("") || txtNV_xnMatKhau.getText().equals("")) {
            JOptionPane.showMessageDialog(this, " vui lòng nhập đủ thông tin");
            return false;
        } else if (!txtNVmatKhau.getText().equals(txtNV_xnMatKhau.getText())) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng mật khẩu !");
            return false;
        } else if (!rdoTruongPhong.isSelected() && !rdoNhanVien.isSelected()) {
            JOptionPane.showMessageDialog(this, " vui lòng chọn vai trò !");
            return false;
        }

        return true;
    }

    void loadNhanVien() {
        DefaultTableModel nvien = (DefaultTableModel) tblNhanVien.getModel();
        nvien.setRowCount(0);
        try {
            List<NhanVienV> listnv = daoNV.selectNV();
            for (NhanVienV nv : listnv) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getMatKhau(),
                    nv.getVaiTro() ? "Trưởng phòng" : "Nhân viên"
                };
                nvien.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu ", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void insertNhanVien() {
        NhanVienV model = getModelNV();
        try {
            daoNV.insertNV(model);
            this.loadNhanVien();
            this.clearNV();
            JOptionPane.showMessageDialog(this, " Thêm mới thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " Thêm mới thất bại", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateNV() {
        NhanVienV model = getModelNV();
        try {
            daoNV.updateNV(model);
            this.loadNhanVien();
//            this.clearNV();
            JOptionPane.showMessageDialog(this, "Update thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update thất bại, vui lòng thử lại !", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void clearNV() {
        txtNV_ID.setText("");
        txtNV_xnMatKhau.setText("");
        txtNVhoTen.setText("");
        txtNVmatKhau.setText("");

        groupNV.clearSelection();
        // lblAnhND.setToolTipText(null);
        // lblAnhND.setIcon(null);
    }

    void deleteNV() {
        int kt = JOptionPane.showConfirmDialog(this, "bạn muốn xóa hay không ? ", "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (kt == JOptionPane.YES_OPTION) {
            try {
                daoNV.deleteNV(IdNV);
                this.loadNhanVien();
                this.clearNV();
                this.setStatusNV(true);
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Xóa thất bại !", "Inane error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void EditNV() {
        try {
            String idNV = (String) tblNhanVien.getValueAt(this.indexNhanVien, 0);
            NhanVienV modell1 = daoNV.findByIdNV(idNV);
            IdNV = idNV;
            if (modell1 != null) {
                this.setModelNV(modell1);
                this.setStatusNV(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vẫn dữ liệu !", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void setModelNV(NhanVienV modelNV) {
        txtNV_ID.setText(modelNV.getMaNV());
        txtNVhoTen.setText(modelNV.getHoTen());
        txtNV_xnMatKhau.setText(modelNV.getMatKhau());
        txtNVmatKhau.setText(modelNV.getMatKhau());
//        lblAnhNV.setToolTipText(modelNV.getHinhAnh());
//        if (modelND.getHinhAnh() != null) {
//            lblAnhND.setIcon(readLogo(lblAnhND, modelND.getHinhAnh()));
//        }

        if (modelNV.getVaiTro() == true) {
            rdoTruongPhong.setSelected(true);
        } else {
            rdoNhanVien.setSelected(true);
        }
    }

    NhanVienV getModelNV() {
        NhanVienV modelNV = new NhanVienV();

        modelNV.setMaNV(txtNV_ID.getText());
        modelNV.setHoTen(txtNVhoTen.getText());
        modelNV.setMatKhau(txtNVmatKhau.getText());

//        modelNV.setHinhAnh(lblAnhND.getToolTipText());
        boolean vt;
        if (rdoTruongPhong.isSelected()) {
            vt = true;
        } else {
            vt = false;
        }
        modelNV.setVaiTro(vt);
        return modelNV;
    }

    void setStatusNV(Boolean insertTable) {

        btnNV_Them.setEnabled(insertTable);
        btnNV_Sua.setEnabled(!insertTable);
        btnNV_Xoa.setEnabled(!insertTable);
        boolean first = this.indexNhanVien > 0;
        boolean last = this.indexNhanVien < tblNhanVien.getRowCount() - 1;
        btnL_NV.setEnabled(!insertTable && first);
        btnNL_NV.setEnabled(!insertTable && first);
        btnR_NV.setEnabled(!insertTable && last);
        btnNR_NV.setEnabled(!insertTable && last);

    }

    // đổi mật khẩu người dùng
    void DoiMK() {
        NhanVienV nv = getModelDoiMatKhau();
        if ((txtPassCu.getText()).equals(mk)) {
            if ((txtPassMoi.getText()).equals(txtPassXN.getText())) {
                try {
                    daoNV.UpdateMK(nv);
                    //  this.loadNgDung();
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, " Đổi mật khảu thất bại, vui lòng thử lại !");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới chưa trùng khớp !");

            }
        } else {
            JOptionPane.showMessageDialog(this, "mật khẩu cũ chưa đúng !");
        }
    }

    NhanVienV getModelDoiMatKhau() {
        NhanVienV nv = new NhanVienV();
        nv.setMaNV(IdLogin);
        nv.setHoTen(TenNgDung);
        nv.setMatKhau(txtPassXN.getText());
        return nv;
    }

    // Chuyên đề
    void loadCD() {
        DefaultTableModel model = (DefaultTableModel) tblChuyenDe.getModel();
        model.setRowCount(0);
        try {
            List<ChuyenDeD> listCD = daoCD.selectCD();
            for (ChuyenDeD cd : listCD) {
                Object[] row = {
                    cd.getMaCD(),
                    cd.getTenCD(),
                    cd.getHocPhi(),
                    cd.getThoiLuong(),
                    cd.getHinh()
                };
                model.addRow(row);

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " lỗi truy vấn dữ liệu", "error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void insertCD() {
        ChuyenDeD model = getModelCD();
        try {
            daoCD.insertCD(model);
            this.loadCD();
            this.clearCD();
            JOptionPane.showMessageDialog(this, " thêm mới thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " thêm mới thất bại !", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateCD() {
        ChuyenDeD model = getModelCD();
        try {
            daoCD.updateCD(model);
            this.loadCD();
//            this.clearCD();
            JOptionPane.showMessageDialog(this, " update thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " update thất bại, vui lòng thử lại !", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteCD() {
        int kt = JOptionPane.showConfirmDialog(this, " bạn thực sự muốn xóa ", "confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (kt == JOptionPane.YES_OPTION) {
            try {
                daoCD.deleteCD(IdCD);
                this.loadCD();
                this.clearCD();
                this.setStatusCD(true);
                JOptionPane.showMessageDialog(this, "Xóa thành công !");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, " xóa thất bại !", "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Boolean checkDeleteCD() {
        KhoaHocK model = daoKH.findByMaCD_KH(IdCD);
        if (model != null) {
            JOptionPane.showMessageDialog(this, " Đang có khóa học cho chuyên đề này !", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    void EditCD() {
        try {
            String id = (String) tblChuyenDe.getValueAt(this.indexCD, 0);
            ChuyenDeD model = daoCD.findByIdCD(id);
            IdCD = id;
            if (model != null) {
                this.setModelCD(model);
                this.setStatusCD(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tru vấn dữ liệu", "error", JOptionPane.ERROR_MESSAGE);
        }

    }

    void setStatusCD(Boolean khTable) {
        btnCD_Them.setEnabled(khTable);
        btnCD_Sua.setEnabled(!khTable);
        btnCD_Xoa.setEnabled(!khTable);
        Boolean fir = this.indexCD > 0;
        Boolean las = this.indexCD < tblChuyenDe.getRowCount() - 1;
        btnNL_CD.setEnabled(!khTable && fir);
        btnL_CD.setEnabled(!khTable && fir);
        btnR_CD.setEnabled(!khTable && las);
        btnNR_CD.setEnabled(!khTable && las);
    }

    void clearCD() {
        txtCD_ID.setText("");
        txtCD_Ten_CD.setText("");
        txtCD_ThoiLuong.setText("");
        txtCD_hocPhi.setText("");
        txtCD_mota.setText("");
        lblAnh_CD.setToolTipText(null);
        lblAnh_CD.setIcon(null);
    }

    void setModelCD(ChuyenDeD modelCD) {
        txtCD_ID.setText(modelCD.getMaCD());
        txtCD_Ten_CD.setText(modelCD.getTenCD());
        txtCD_ThoiLuong.setText(String.valueOf(modelCD.getThoiLuong()));
        txtCD_hocPhi.setText(String.valueOf(modelCD.getHocPhi()));
//        System.out.println("---------value of set ---- " + String.valueOf(modelCD.getHocPhi()));
//        System.out.println("---------parseDouble set ---- " + Double.toString(modelCD.getHocPhi()));
        txtCD_mota.setText(modelCD.getMoTa());
        lblAnh_CD.setToolTipText(modelCD.getHinh());
        if (modelCD.getHinh() != null) {
            lblAnh_CD.setIcon(readLogo(lblAnh_CD, modelCD.getHinh()));
        }
    }

    ChuyenDeD getModelCD() {
        ChuyenDeD model = new ChuyenDeD();
        model.setMaCD(txtCD_ID.getText());
        model.setTenCD(txtCD_Ten_CD.getText());
        model.setThoiLuong(Integer.valueOf(txtCD_ThoiLuong.getText()));
        model.setHocPhi(Double.valueOf(txtCD_hocPhi.getText()));
//        System.out.println("---------value of ---- " + Double.valueOf(txtCD_hocPhi.getText()));
//        System.out.println("---------parseDouble ---- " + Double.parseDouble(txtCD_hocPhi.getText()));
        model.setMoTa(txtCD_mota.getText());
        model.setHinh(lblAnh_CD.getToolTipText());
        return model;
    }

    public Boolean checkCD() {

        if (txtCD_ID.getText().equals("") || txtCD_Ten_CD.getText().equals("") || txtCD_ThoiLuong.getText().equals("") || txtCD_hocPhi.getText().equals("") || txtCD_mota.getText().equals("")) {
            JOptionPane.showMessageDialog(this, " không được để trống !", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Double.parseDouble(txtCD_ThoiLuong.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Thời lượng không được nhỏ hơn 0 !", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.parseInt(txtCD_hocPhi.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Học phí không được nhỏ hơn 0", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

//    // Khóa học
    void loadKH() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            List<KhoaHocK> list = daoKH.selectKH();
            for (KhoaHocK dt : list) {
                Object[] row = {
                    dt.getMaKH(),
                    dt.getMaCD(),
                    dt.getThoiLuong(),
                    dt.getHocPhi(),
                    ToString(dt.getNgayKG()),
                    dt.getMaNV(),
                    ToString(dt.getNgayTao())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " Lỗi truy vấn dữ liệu", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void editKH() {
        try {
            Integer idkh = (Integer) tblKhoaHoc.getValueAt(this.indexKhoaHoc, 0);
            IdKH = idkh;
            KhoaHocK model = daoKH.findByIdKH(idkh);
            if (model != null) {
                this.setModelKH(model);
                this.setStatusKH(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " Lỗi truy vấn dữ liệu", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void insertKH() {
        KhoaHocK model = getModelKH();
        try {
            daoKH.insertKH(model);

            this.loadKH();
            this.clearKH();
            JOptionPane.showMessageDialog(this, "thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, " thêm mới thất bại", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateKH() {
        KhoaHocK model = getModelKH();
        try {
            daoKH.updateKH(model);
            this.loadKH();
            JOptionPane.showMessageDialog(this, " update thành công");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "update thất bại", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteKH() {
        int kt = JOptionPane.showConfirmDialog(this, "bạn thực sự muốn xóa", "confirm", JOptionPane.YES_NO_OPTION); //JOptionPane.QUESTION_MESSAGE
        if (kt == JOptionPane.YES_OPTION) {
            try {
                daoKH.deleteKH(IdKH);
                this.loadKH();
                this.clearKH();
                this.setStatusKH(true);
                JOptionPane.showMessageDialog(this, "xóa thành công");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, " xóa thất bại, vui lòng thử lại", "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void setStatusKH(boolean inserttable) {
        btnKH_Them.setEnabled(inserttable);
        btnKH_sua.setEnabled(!inserttable);
        btnKH_xoa.setEnabled(!inserttable);
        boolean fir = this.indexKhoaHoc > 0;
        boolean las = this.indexKhoaHoc < tblKhoaHoc.getRowCount() - 1;
        btnNL_KH.setEnabled(!inserttable && fir);
        btnL_KH.setEnabled(!inserttable && fir);
        btnNR_KH.setEnabled(!inserttable && las);
        btnR_KH.setEnabled(!inserttable && las);

        btnHocVien.setVisible(!inserttable);
    }

    void clearKH() {
        KhoaHocK model = new KhoaHocK();
        ChuyenDeD chuyenDe = (ChuyenDeD) cbbChuyenDe.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        txtKH_HocPhi.setText("");
        txtKH_ghiChu.setText("");
        txtKH_ngayKhaiGiang.setText("");
        txtKH_ngayTao.setText("");
//        model.setNgayTao(new Date());
        txtKH_thoiLuong.setText("");
        model.setMaNV(IdLogin);
    }

    void setModelKH(KhoaHocK model) {
        cbbChuyenDe.setToolTipText(String.valueOf(model.getMaKH()));
        cbbChuyenDe.getModel().setSelectedItem(daoCD.findByIdCD(model.getMaCD()));
        txtKH_ngayKhaiGiang.setText(ToString(model.getNgayKG()));
        txtKH_HocPhi.setText(String.valueOf(model.getHocPhi()));
        txtKH_thoiLuong.setText(String.valueOf(model.getThoiLuong()));
        txtKH_MaNV.setText(model.getMaNV());
        txtKH_ngayTao.setText(ToString(model.getNgayTao()));
        txtKH_ghiChu.setText(model.getGhiChu());
        maKH_O_HV = model.getMaKH();
    }

    KhoaHocK getModelKH() {
        KhoaHocK model = new KhoaHocK();
        ChuyenDeD chuyenDe = (ChuyenDeD) cbbChuyenDe.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setNgayKG(toDate(txtKH_ngayKhaiGiang.getText()));
        model.setHocPhi(Double.valueOf(txtKH_HocPhi.getText()));
        model.setThoiLuong(Integer.valueOf(txtKH_thoiLuong.getText()));
        model.setGhiChu(txtKH_ghiChu.getText());
        model.setMaNV(IdLogin);
        model.setNgayTao(toDate(txtKH_ngayTao.getText()));
        model.setMaKH(Integer.valueOf(cbbChuyenDe.getToolTipText()));

        return model;
    }

    public boolean checkKH() {

        if (txtKH_HocPhi.getText().equals("") || txtKH_MaNV.getText().equals("") || txtKH_ghiChu.getText().equals("") || txtKH_ngayKhaiGiang.getText().equals("") || txtKH_ngayTao.getText().equals("") || txtKH_thoiLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Không được để trống");
            return false;
        } else if (Integer.parseInt(txtKH_thoiLuong.getText()) < 0) {
            JOptionPane.showMessageDialog(this, " Thời lượng phải >= 0", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Double.parseDouble(txtKH_HocPhi.getText()) < 0) {
            JOptionPane.showMessageDialog(this, "Học phí không được nhỏ hơn 0", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    void selectComboBoxKH() {
        ChuyenDeD chuyenDe = (ChuyenDeD) cbbChuyenDe.getSelectedItem();
        txtKH_thoiLuong.setText(String.valueOf(chuyenDe.getThoiLuong()));
        txtKH_HocPhi.setText(String.valueOf(chuyenDe.getHocPhi()));
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbChuyenDe.getModel();// <ChuyenDe>
        model.removeAllElements();
        try {
            List<ChuyenDeD> list = daoCD.selectCD();
            for (ChuyenDeD cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu !", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    // Hoc vien
    void fillComboBox_HocVien() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbHocVien.getModel();// <ChuyenDe>
        model.removeAllElements();
        try {
            List<NguoiHocN> list = daoN_H.selectByCourseNH(maKH_O_HV);
            for (NguoiHocN nh : list) {
                model.addElement(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu !", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void fillGridView() {

        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        model.setRowCount(0);
        try {
            String sql = "SELECT hv.*, nh.HoTen FROM HocVien hv JOIN NguoiHoc nh ON nh.MaNH=hv.MaNH WHERE MaKH=?";
            ResultSet rs = jdbcDao.executeQuery(sql, maKH_O_HV);
            while (rs.next()) {
                double diem = rs.getDouble("Diem");
                Object[] row = {rs.getInt("MaHV"), rs.getString("MaNH"),
                    rs.getString("HoTen"), diem, false
                };
                if (rdoTatCa.isSelected()) {
                    model.addRow(row);
                } else if (rdoDaNhap.isSelected() && diem >= 0) {
                    model.addRow(row);
                } else if (rdoChuaNhap.isSelected() && diem < 0) {
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn học viên!", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void insertHV() {
        NguoiHocN nguoiHoc = (NguoiHocN) cbbHocVien.getSelectedItem();
        HocVienH model = new HocVienH();
        model.setMaKH(IdKH);
//        System.out.println("khach hang: " + maKH_O_HV);
        model.setMaNH(nguoiHoc.getMaNH());
        model.setDiem(Double.valueOf(txtHV_diem.getText()));
        try {
            daoHV.insertHV(model);
            this.fillComboBox_HocVien();
            this.fillGridView();
        } catch (Exception e) {
            e.printStackTrace();
//            DialogHelper.alert(this, "Lỗi thêm học viên vào khóa học!");//
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn khóa học !!", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateHV() {
        for (int i = 0; i < tblHocVien.getRowCount(); i++) {
            Integer mahv = (Integer) tblHocVien.getValueAt(i, 0);
            String manh = (String) tblHocVien.getValueAt(i, 1);
            Double diem = (Double) tblHocVien.getValueAt(i, 3);
            Boolean isDelete = (Boolean) tblHocVien.getValueAt(i, 4);
            if (isDelete) {
                daoHV.deleteHV(mahv);
            } else {
                HocVienH model = new HocVienH();
                model.setMaHV(mahv);
                model.setMaKH(IdKH);
                model.setMaNH(manh);
                model.setDiem(diem);
                daoHV.updateHV(model);
            }
        }
        this.fillComboBox_HocVien();
        this.fillGridView();
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    }

    // người học
    void loadNH() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtNH_TimKiem.getText();
            List<NguoiHocN> list = daoN_H.selectByKeywordNH(keyword);
            for (NguoiHocN nh : list) {
                Object[] row = {
                    nh.getMaNH(),
                    nh.getHoTen(),
                    nh.getGioiTinh() ? "Nam" : "Nữ",
                    ToString(nh.getNgaySinh()),
                    nh.getDienThoai(),
                    nh.getEmail(),
                    nh.getMaNV(),
                    ToString(nh.getNgayDK())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setStatusNH(boolean insertable) {
        txtNH_ID.setEditable(insertable);
        btnNH_them.setEnabled(insertable);
        btnNH_sua.setEnabled(!insertable);
        btnNH_Xoa.setEnabled(!insertable);
        boolean first = this.indexNguoiHoc > 0;
        boolean last = this.indexNguoiHoc < tblNguoiHoc.getRowCount() - 1;
        btnNL_NH.setEnabled(!insertable && first);
        btnL_NH.setEnabled(!insertable && first);
        btnR_NH.setEnabled(!insertable && last);
        btnNR_NH.setEnabled(!insertable && last);
    }

    void insertNH() {
        NguoiHocN model = getModelNH();
        try {
            daoN_H.insertNH(model);
            this.loadNH();
            this.clearNH();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void updateNH() {
        NguoiHocN model = getModelNH();
        try {
            daoN_H.updateNH(model);
            this.loadNH();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void deleteNH() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa người học này?")) {
            String manh = txtNH_ID.getText();
            try {
                daoN_H.deleteNH(manh);
                this.loadNH();
                this.clearNH();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clearNH() {
        NguoiHocN model = new NguoiHocN();
        model.setGioiTinh(cbbGioiTinh_NH.getSelectedIndex() == 0);
        txtNH_ID.setText("");
        txtNH_DienThoai.setText("");
        txtNH_Email.setText("");
        txtNH_GhiChu.setText("");
        txtNH_HoTen.setText("");
        txtNH_NgaySinh.setText("");

//        this.setStatusNH(true);
    }

    void editNH() {
        try {
            String manh = (String) tblNguoiHoc.getValueAt(this.indexNguoiHoc, 0);
            NguoiHocN model = daoN_H.findByIdNH(manh);
            if (model != null) {
                this.setModelNH(model);
                this.setStatusNH(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModelNH(NguoiHocN model) {
        txtNH_ID.setText(model.getMaNH());
        txtNH_HoTen.setText(model.getHoTen());
        System.out.println(model.getHoTen());
        cbbGioiTinh_NH.setSelectedIndex(model.getGioiTinh() ? 0 : 1);
        txtNH_NgaySinh.setText(ToString(model.getNgaySinh()));
        txtNH_DienThoai.setText(model.getDienThoai());
        txtNH_Email.setText(model.getEmail());
        txtNH_GhiChu.setText(model.getGhiChu());
    }

    NguoiHocN getModelNH() {
        NguoiHocN model = new NguoiHocN();
        model.setMaNH(txtNH_ID.getText());
        model.setHoTen(txtNH_HoTen.getText());
        model.setGioiTinh(cbbGioiTinh_NH.getSelectedIndex() == 0);
        model.setNgaySinh(toDate(txtNH_NgaySinh.getText()));
        model.setDienThoai(txtNH_DienThoai.getText());
        model.setEmail(txtNH_Email.getText());
        model.setGhiChu(txtNH_GhiChu.getText());
        model.setMaNV(IdLogin);
        model.setNgayDK(new Date());

        return model;
    }

    public boolean checkNH() {

        if (txtNH_ID.getText().equals("") || txtNH_DienThoai.getText().equals("") || txtNH_Email.getText().equals("") || txtNH_GhiChu.getText().equals("")
                || txtNH_HoTen.getText().equals("") || txtNH_NgaySinh.getText().equals("")) {
            JOptionPane.showMessageDialog(this, " vui lòng nhập đủ thông tin", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!(txtNH_DienThoai.getText()).matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this, "vui lòng nhập đúng định dạng SĐT 10 số ", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!(txtNH_Email.getText()).matches("\\w+@\\w+\\.\\w+")) {
            JOptionPane.showMessageDialog(this, " vui lòng nhập đúng định dạng email", "error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

//    // Thong ke
    void fillComboBoxKhoaHoc_TK() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTK_KhoaHoc.getModel();
        model.removeAllElements();
        List<KhoaHocK> list = daoKH.selectKH();
        for (KhoaHocK kh : list) {
            model.addElement(kh);
        }
        cbbTK_KhoaHoc.setSelectedIndex(0);
    }

    void fillComboBoxNam_TK() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTK_Nam.getModel();
        model.removeAllElements();
        List<KhoaHocK> list = daoKH.selectKH();
        for (KhoaHocK kh : list) {
            int nam = kh.getNgayKG().getYear() + 1900;
            if (model.getIndexOf(nam) < 0) {
                model.addElement(nam);
            }
        }
        cbbTK_Nam.setSelectedIndex(0);
    }

    void fillTableBangDiem_TK() {
        DefaultTableModel model = (DefaultTableModel) tblTK_BangDiem.getModel();
        model.setRowCount(0);
        KhoaHocK kh = (KhoaHocK) cbbTK_KhoaHoc.getSelectedItem();
        List<Object[]> list = daoTK.getBangDiem(kh.getMaKH());
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    void fillTableNguoiHoc_TK() {
        DefaultTableModel model = (DefaultTableModel) tblTK_NguoiHoc.getModel();
        model.setRowCount(0);
        List<Object[]> list = daoTK.getNguoiHoc();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    void fillTableKhoaHoc_TK() {
        DefaultTableModel model = (DefaultTableModel) tblTK_TongHop.getModel();
        model.setRowCount(0);
        List<Object[]> list = daoTK.getDiemTheoChuyenDe();
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    void fillTableDoanhThu_TK() {
        DefaultTableModel model = (DefaultTableModel) tblTK_DoanhThu.getModel();
        model.setRowCount(0);
        int nam = Integer.parseInt(cbbTK_Nam.getSelectedItem().toString());
        List<Object[]> list = daoTK.getDoanhThu(nam);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPaneMenu = new javax.swing.JPanel();
        lblGif = new javax.swing.JLabel();
        lblGach = new javax.swing.JLabel();
        lblBackG = new javax.swing.JLabel();
        JPaneMenuLogin2 = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnPhanTich = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnQuanLi = new javax.swing.JButton();
        btnHeThong = new javax.swing.JButton();
        JPaneMenuLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        JPaneTime = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        background = new javax.swing.JLabel();
        jPaneExit = new javax.swing.JPanel();
        right = new javax.swing.JLabel();
        top = new javax.swing.JLabel();
        left = new javax.swing.JLabel();
        bot = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCan = new javax.swing.JButton();
        btnYes = new javax.swing.JButton();
        jPaneForm = new javax.swing.JPanel();
        JPaneHome = new javax.swing.JPanel();
        slide = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        JPaneThongKe = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblTK_NguoiHoc = new javax.swing.JTable();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblTK_BangDiem = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        cbbTK_KhoaHoc = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblTK_TongHop = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblTK_DoanhThu = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        cbbTK_Nam = new javax.swing.JComboBox<>();
        lblThongKe = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        backg1 = new javax.swing.JLabel();
        JPanePhanTich = new javax.swing.JPanel();
        JPaneQuanLi = new javax.swing.JPanel();
        lblQLnguoiHoc = new javax.swing.JLabel();
        lblQLchuyenDe = new javax.swing.JLabel();
        lblQLkhoaHoc = new javax.swing.JLabel();
        lblQLnhanVien = new javax.swing.JLabel();
        lblQLhocVien = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        btnNguoiHoc = new javax.swing.JButton();
        btnChuyenDe = new javax.swing.JButton();
        btnKhoaHoc = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jTabbQLNguoiHoc = new javax.swing.JTabbedPane();
        jPanelQLduAn = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtNH_ID = new javax.swing.JTextField();
        txtNH_NgaySinh = new javax.swing.JTextField();
        txtNH_HoTen = new javax.swing.JTextField();
        txtNH_Email = new javax.swing.JTextField();
        txtNH_DienThoai = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnNH_them = new javax.swing.JButton();
        btnNH_sua = new javax.swing.JButton();
        btnNH_Xoa = new javax.swing.JButton();
        btnNH_moi = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        lblDA_inEx = new javax.swing.JLabel();
        btnNL_NH = new javax.swing.JButton();
        btnL_NH = new javax.swing.JButton();
        btnNR_NH = new javax.swing.JButton();
        btnR_NH = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtNH_GhiChu = new javax.swing.JTextArea();
        cbbGioiTinh_NH = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel25 = new javax.swing.JPanel();
        txtNH_TimKiem = new javax.swing.JTextField();
        btnNH_TimKiem = new javax.swing.JLabel();
        jTabbQLKhoaHoc = new javax.swing.JTabbedPane();
        jPanelQLnguoiDung = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtKH_HocPhi = new javax.swing.JTextField();
        txtKH_ngayKhaiGiang = new javax.swing.JTextField();
        txtKH_thoiLuong = new javax.swing.JTextField();
        txtKH_ngayTao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnKH_Them = new javax.swing.JButton();
        btnKH_sua = new javax.swing.JButton();
        btnKH_xoa = new javax.swing.JButton();
        btnKH_moi = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btnHocVien = new javax.swing.JLabel();
        btnNL_KH = new javax.swing.JButton();
        btnL_KH = new javax.swing.JButton();
        btnNR_KH = new javax.swing.JButton();
        btnR_KH = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtKH_ghiChu = new javax.swing.JTextArea();
        cbbChuyenDe = new javax.swing.JComboBox<>();
        txtKH_MaNV = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();
        jTabbQLHocVien = new javax.swing.JTabbedPane();
        jPanelQLnguoiDung2 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        txtHV_diem = new javax.swing.JTextField();
        cbbHocVien = new javax.swing.JComboBox<>();
        btnHV_them = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoDaNhap = new javax.swing.JRadioButton();
        rdoChuaNhap = new javax.swing.JRadioButton();
        btnHV_CapNhat = new javax.swing.JButton();
        jTabbQLNhanVien = new javax.swing.JTabbedPane();
        jPanelQLnguoiDung1 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        txtNV_ID = new javax.swing.JTextField();
        txtNVmatKhau = new javax.swing.JTextField();
        txtNV_xnMatKhau = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        btnNV_Them = new javax.swing.JButton();
        btnNV_Sua = new javax.swing.JButton();
        btnNV_Xoa = new javax.swing.JButton();
        btnNV_Moi = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        btnNL_NV = new javax.swing.JButton();
        btnL_NV = new javax.swing.JButton();
        btnNR_NV = new javax.swing.JButton();
        btnR_NV = new javax.swing.JButton();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        txtNVhoTen = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jTabbQLChuyenDe = new javax.swing.JTabbedPane();
        jPanelQLsanPham = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtCD_ID = new javax.swing.JTextField();
        txtCD_Ten_CD = new javax.swing.JTextField();
        txtCD_ThoiLuong = new javax.swing.JTextField();
        txtCD_hocPhi = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btnCD_Them = new javax.swing.JButton();
        btnCD_Sua = new javax.swing.JButton();
        btnCD_Xoa = new javax.swing.JButton();
        btnCD_Moi = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        lblDA_inEx1 = new javax.swing.JLabel();
        btnNL_CD = new javax.swing.JButton();
        btnL_CD = new javax.swing.JButton();
        btnNR_CD = new javax.swing.JButton();
        btnR_CD = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        lblAnh_CD = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtCD_mota = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChuyenDe = new javax.swing.JTable();
        backg = new javax.swing.JLabel();
        JPaneHeThong = new javax.swing.JPanel();
        lblLogout = new javax.swing.JLabel();
        lblThoat = new javax.swing.JLabel();
        lblGioiThieu = new javax.swing.JLabel();
        lblTaiKhoan = new javax.swing.JLabel();
        JPaneGioiThieu = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        JPaneTaiKhoan = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        lblNameTK = new javax.swing.JLabel();
        lblShow1 = new javax.swing.JLabel();
        lblHide1 = new javax.swing.JLabel();
        txtPassCu = new javax.swing.JPasswordField();
        lblShow2 = new javax.swing.JLabel();
        lblHide2 = new javax.swing.JLabel();
        txtPassMoi = new javax.swing.JPasswordField();
        lblShow3 = new javax.swing.JLabel();
        lblHide3 = new javax.swing.JLabel();
        txtPassXN = new javax.swing.JPasswordField();
        lblThemTK = new javax.swing.JLabel();
        lblNewTK = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JPaneAbout = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        backk = new javax.swing.JLabel();
        JPaneThoat = new javax.swing.JPanel();
        right1 = new javax.swing.JLabel();
        top1 = new javax.swing.JLabel();
        left1 = new javax.swing.JLabel();
        bot1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCan1 = new javax.swing.JButton();
        btnYes1 = new javax.swing.JButton();
        JPaneLoogin = new javax.swing.JPanel();
        txtUserName = new javax.swing.JTextField();
        lblEyyy = new javax.swing.JLabel();
        lblEye = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        chkGhiNho = new javax.swing.JCheckBox();
        btnLoginOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblLogin = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_BackG_Home = new javax.swing.JLabel();
        JPaneLogout = new javax.swing.JPanel();
        txtUserName1 = new javax.swing.JTextField();
        lblShowLogout = new javax.swing.JLabel();
        lblHideLogout = new javax.swing.JLabel();
        txtPass1 = new javax.swing.JPasswordField();
        chkGhiNho1 = new javax.swing.JCheckBox();
        btnLoginOK1 = new javax.swing.JButton();
        btnEx = new javax.swing.JButton();
        lblLogin1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÍ BẤT ĐỘNG SẢN");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1280, 590));
        jPanel4.setLayout(null);

        jPaneMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPaneMenu.setMinimumSize(new java.awt.Dimension(160, 630));
        jPaneMenu.setPreferredSize(new java.awt.Dimension(160, 590));
        jPaneMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPaneMenuMouseClicked(evt);
            }
        });
        jPaneMenu.setLayout(null);

        lblGif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGif.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblGif.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblGif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGifMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblGifMousePressed(evt);
            }
        });
        jPaneMenu.add(lblGif);
        lblGif.setBounds(0, 100, 160, 50);

        lblGach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/gach.png"))); // NOI18N
        lblGach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblGachMousePressed(evt);
            }
        });
        jPaneMenu.add(lblGach);
        lblGach.setBounds(0, 100, 40, 50);

        lblBackG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/bac.jpg"))); // NOI18N
        jPaneMenu.add(lblBackG);
        lblBackG.setBounds(0, 0, 40, 590);

        JPaneMenuLogin2.setOpaque(false);
        JPaneMenuLogin2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPaneMenuLogin2MouseClicked(evt);
            }
        });
        JPaneMenuLogin2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnHome.setText("TRANG CHỦ");
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHome.setOpaque(false);
        btnHome.setPreferredSize(new java.awt.Dimension(73, 25));
        btnHome.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnHome.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });
        JPaneMenuLogin2.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 177, 65));

        btnPhanTich.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnPhanTich.setForeground(new java.awt.Color(255, 255, 255));
        btnPhanTich.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnPhanTich.setText("PHÂN TÍCH");
        btnPhanTich.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnPhanTich.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPhanTich.setOpaque(false);
        btnPhanTich.setPreferredSize(new java.awt.Dimension(73, 25));
        btnPhanTich.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnPhanTich.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnPhanTich.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPhanTichMouseClicked(evt);
            }
        });
        btnPhanTich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanTichActionPerformed(evt);
            }
        });
        JPaneMenuLogin2.add(btnPhanTich, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 177, 65));

        btnThongKe.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThongKe.setOpaque(false);
        btnThongKe.setPreferredSize(new java.awt.Dimension(73, 25));
        btnThongKe.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnThongKe.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongKeMouseClicked(evt);
            }
        });
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });
        JPaneMenuLogin2.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 177, 65));

        btnQuanLi.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnQuanLi.setForeground(new java.awt.Color(255, 255, 255));
        btnQuanLi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnQuanLi.setText("QUẢN LÍ");
        btnQuanLi.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnQuanLi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnQuanLi.setOpaque(false);
        btnQuanLi.setPreferredSize(new java.awt.Dimension(73, 25));
        btnQuanLi.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnQuanLi.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnQuanLi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQuanLiMouseClicked(evt);
            }
        });
        btnQuanLi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLiActionPerformed(evt);
            }
        });
        JPaneMenuLogin2.add(btnQuanLi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 177, 65));

        btnHeThong.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnHeThong.setForeground(new java.awt.Color(255, 255, 255));
        btnHeThong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnHeThong.setText("HỆ THỐNG");
        btnHeThong.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnHeThong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHeThong.setOpaque(false);
        btnHeThong.setPreferredSize(new java.awt.Dimension(73, 25));
        btnHeThong.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnHeThong.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnHeThong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHeThongMouseClicked(evt);
            }
        });
        btnHeThong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHeThongActionPerformed(evt);
            }
        });
        JPaneMenuLogin2.add(btnHeThong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 177, 65));

        jPaneMenu.add(JPaneMenuLogin2);
        JPaneMenuLogin2.setBounds(0, 0, 160, 590);

        JPaneMenuLogin.setOpaque(false);
        JPaneMenuLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JPaneMenuLoginMouseClicked(evt);
            }
        });
        JPaneMenuLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnLogin.setText("LOGIN");
        btnLogin.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.setOpaque(false);
        btnLogin.setPreferredSize(new java.awt.Dimension(73, 25));
        btnLogin.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnLogin.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        JPaneMenuLogin.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 177, 65));

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but.png"))); // NOI18N
        btnExit.setText("EXIT");
        btnExit.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setOpaque(false);
        btnExit.setPreferredSize(new java.awt.Dimension(73, 25));
        btnExit.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but1.png"))); // NOI18N
        btnExit.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/button/but2.png"))); // NOI18N
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExitMousePressed(evt);
            }
        });
        JPaneMenuLogin.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 177, 65));

        jPaneMenu.add(JPaneMenuLogin);
        JPaneMenuLogin.setBounds(0, 0, 160, 590);

        JPaneTime.setBackground(new java.awt.Color(0, 51, 255));
        JPaneTime.setOpaque(false);

        lblTime.setBackground(new java.awt.Color(0, 51, 255));
        lblTime.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel1.setBackground(new java.awt.Color(0, 0, 255));
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout JPaneTimeLayout = new javax.swing.GroupLayout(JPaneTime);
        JPaneTime.setLayout(JPaneTimeLayout);
        JPaneTimeLayout.setHorizontalGroup(
            JPaneTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPaneTimeLayout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(JPaneTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );
        JPaneTimeLayout.setVerticalGroup(
            JPaneTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPaneTimeLayout.createSequentialGroup()
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(JPaneTimeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        jPaneMenu.add(JPaneTime);
        JPaneTime.setBounds(0, 556, 160, 34);

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/logo1.png"))); // NOI18N
        jPaneMenu.add(logo);
        logo.setBounds(49, 0, 100, 114);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/bac.jpg"))); // NOI18N
        background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backgroundMouseClicked(evt);
            }
        });
        jPaneMenu.add(background);
        background.setBounds(0, 0, 160, 590);

        jPanel4.add(jPaneMenu);
        jPaneMenu.setBounds(0, 0, 160, 590);

        jPaneExit.setBackground(new java.awt.Color(255, 255, 255));
        jPaneExit.setLayout(null);

        right.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panRight.png"))); // NOI18N
        jPaneExit.add(right);
        right.setBounds(459, -10, 670, 610);

        top.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        top.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panTopp.png"))); // NOI18N
        jPaneExit.add(top);
        top.setBounds(0, 0, 982, 261);

        left.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panLeft.png"))); // NOI18N
        jPaneExit.add(left);
        left.setBounds(0, 0, 643, 590);

        bot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panBot.png"))); // NOI18N
        jPaneExit.add(bot);
        bot.setBounds(126, 330, 1010, 270);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("Bạn muốn kết thúc chương trình ?");
        jPaneExit.add(jLabel6);
        jLabel6.setBounds(400, 220, 330, 45);

        btnCan.setText("Không");
        btnCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanActionPerformed(evt);
            }
        });
        jPaneExit.add(btnCan);
        btnCan.setBounds(630, 310, 90, 34);

        btnYes.setText("Đồng ý");
        btnYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYesActionPerformed(evt);
            }
        });
        jPaneExit.add(btnYes);
        btnYes.setBounds(390, 310, 90, 34);

        jPanel4.add(jPaneExit);
        jPaneExit.setBounds(160, 0, 1120, 590);

        jPaneForm.setBackground(new java.awt.Color(255, 255, 255));
        jPaneForm.setPreferredSize(new java.awt.Dimension(1120, 590));
        jPaneForm.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPaneFormAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jPaneForm.setLayout(null);

        JPaneHome.setBackground(new java.awt.Color(255, 255, 255));
        JPaneHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        JPaneHome.add(slide, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 132, 788, 391));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee2.png"))); // NOI18N
        jLabel5.setText("TRANG CHỦ");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JPaneHome.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 70));

        jPaneForm.add(JPaneHome);
        JPaneHome.setBounds(160, 0, 1120, 590);

        JPaneThongKe.setBackground(new java.awt.Color(255, 255, 0));
        JPaneThongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbedPane1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel22.setOpaque(false);

        tblTK_NguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Năm", "Số người học", "Đầu tiên", "Sau cùng"
            }
        ));
        tblTK_NguoiHoc.setOpaque(false);
        jScrollPane6.setViewportView(tblTK_NguoiHoc);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Người học", jPanel22);

        jPanel23.setOpaque(false);

        tblTK_BangDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã người học", "Học và tên", "Điểm", "Xếp loại"
            }
        ));
        tblTK_BangDiem.setOpaque(false);
        jScrollPane7.setViewportView(tblTK_BangDiem);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel33.setText("Khóa học :");

        cbbTK_KhoaHoc.setOpaque(false);
        cbbTK_KhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTK_KhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel33)
                .addGap(38, 38, 38)
                .addComponent(cbbTK_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTK_KhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Bảng điểm", jPanel23);

        jPanel24.setOpaque(false);

        tblTK_TongHop.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chuyên đề", "Tổng số học viên", "Cao nhất ", "Thấp nhất", "Trung bình"
            }
        ));
        tblTK_TongHop.setOpaque(false);
        jScrollPane8.setViewportView(tblTK_TongHop);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Tổng hợp điểm", jPanel24);

        jPanel5.setOpaque(false);

        tblTK_DoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chuyên đề", "Tổng số học viên", "Cao nhất ", "Thấp nhất", "Trung bình"
            }
        ));
        tblTK_DoanhThu.setOpaque(false);
        jScrollPane12.setViewportView(tblTK_DoanhThu);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel34.setText("Năm :");

        cbbTK_Nam.setOpaque(false);
        cbbTK_Nam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTK_NamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 1215, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel34)
                .addGap(33, 33, 33)
                .addComponent(cbbTK_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(cbbTK_Nam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Doanh thu", jPanel5);

        JPaneThongKe.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1220, 510));

        lblThongKe.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThongKe.setText("Thống kê");
        JPaneThongKe.add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee333.png"))); // NOI18N
        JPaneThongKe.add(lblTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 70));

        backg1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/6.png"))); // NOI18N
        JPaneThongKe.add(backg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1240, 520));

        jPaneForm.add(JPaneThongKe);
        JPaneThongKe.setBounds(40, 0, 1240, 590);

        JPanePhanTich.setBackground(new java.awt.Color(51, 255, 0));

        javax.swing.GroupLayout JPanePhanTichLayout = new javax.swing.GroupLayout(JPanePhanTich);
        JPanePhanTich.setLayout(JPanePhanTichLayout);
        JPanePhanTichLayout.setHorizontalGroup(
            JPanePhanTichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1240, Short.MAX_VALUE)
        );
        JPanePhanTichLayout.setVerticalGroup(
            JPanePhanTichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        jPaneForm.add(JPanePhanTich);
        JPanePhanTich.setBounds(40, 0, 1240, 590);

        JPaneQuanLi.setForeground(new java.awt.Color(255, 0, 51));
        JPaneQuanLi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQLnguoiHoc.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblQLnguoiHoc.setForeground(new java.awt.Color(255, 255, 255));
        lblQLnguoiHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLnguoiHoc.setText("Quản lí người học");
        JPaneQuanLi.add(lblQLnguoiHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblQLchuyenDe.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblQLchuyenDe.setForeground(new java.awt.Color(255, 255, 255));
        lblQLchuyenDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLchuyenDe.setText("Quản lí chuyên đề");
        JPaneQuanLi.add(lblQLchuyenDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblQLkhoaHoc.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblQLkhoaHoc.setForeground(new java.awt.Color(255, 255, 255));
        lblQLkhoaHoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLkhoaHoc.setText("Quản lí khóa học");
        JPaneQuanLi.add(lblQLkhoaHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblQLnhanVien.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblQLnhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblQLnhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLnhanVien.setText("Quản lí nhân viên");
        JPaneQuanLi.add(lblQLnhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblQLhocVien.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblQLhocVien.setForeground(new java.awt.Color(255, 255, 255));
        lblQLhocVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblQLhocVien.setText("Quản lí học viên");
        JPaneQuanLi.add(lblQLhocVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 370, 70));

        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee333.png"))); // NOI18N
        JPaneQuanLi.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 70));

        btnNguoiHoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNguoiHoc.setText("NGƯỜI HỌC");
        btnNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNguoiHocActionPerformed(evt);
            }
        });
        JPaneQuanLi.add(btnNguoiHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 120, 70));

        btnChuyenDe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChuyenDe.setText("CHUYÊN ĐỀ");
        btnChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenDeActionPerformed(evt);
            }
        });
        JPaneQuanLi.add(btnChuyenDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 120, 70));

        btnKhoaHoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnKhoaHoc.setText("KHÓA HỌC");
        btnKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoaHocActionPerformed(evt);
            }
        });
        JPaneQuanLi.add(btnKhoaHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 120, 70));

        btnNhanVien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNhanVien.setText("NHÂN VIÊN");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });
        JPaneQuanLi.add(btnNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 120, 70));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/loxo.png"))); // NOI18N
        JPaneQuanLi.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 70, 520));

        jTabbQLNguoiHoc.setBackground(new java.awt.Color(255, 255, 255));
        jTabbQLNguoiHoc.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbQLNguoiHoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbQLNguoiHoc.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbQLNguoiHocAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanelQLduAn.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelQLduAn.setOpaque(false);
        jPanelQLduAn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel37.setText("ID người học :");
        jPanelQLduAn.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 20, -1, -1));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel38.setText("Họ và tên :");
        jPanelQLduAn.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 71, -1, -1));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel39.setText("Ngày sinh :");
        jPanelQLduAn.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 120, -1, -1));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel40.setText("Email :");
        jPanelQLduAn.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 170, -1, -1));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel41.setText("Điện thoại :");
        jPanelQLduAn.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 220, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel42.setText("Ghi chú :");
        jPanelQLduAn.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 273, -1, -1));

        txtNH_ID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLduAn.add(txtNH_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 14, 627, 30));

        txtNH_NgaySinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLduAn.add(txtNH_NgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 114, 627, 30));

        txtNH_HoTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLduAn.add(txtNH_HoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 64, 627, 30));

        txtNH_Email.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLduAn.add(txtNH_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 164, 627, 30));

        txtNH_DienThoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLduAn.add(txtNH_DienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 214, 230, 30));

        jPanel6.setOpaque(false);

        btnNH_them.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNH_them.setText("Thêm");
        btnNH_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNH_themActionPerformed(evt);
            }
        });

        btnNH_sua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNH_sua.setText("Sửa");
        btnNH_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNH_suaActionPerformed(evt);
            }
        });

        btnNH_Xoa.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNH_Xoa.setText("Xóa");
        btnNH_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNH_XoaActionPerformed(evt);
            }
        });

        btnNH_moi.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNH_moi.setText("Mới");
        btnNH_moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNH_moiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(btnNH_them, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNH_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNH_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNH_moi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNH_them, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNH_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNH_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNH_moi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanelQLduAn.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 393, -1, -1));

        jPanel7.setOpaque(false);
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDA_inEx.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDA_inEx.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDA_inEx.setText("In ex");
        lblDA_inEx.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblDA_inEx.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDA_inExMouseClicked(evt);
            }
        });
        jPanel7.add(lblDA_inEx, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 42));

        btnNL_NH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNL_NH.setText("|<");
        btnNL_NH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNL_NHActionPerformed(evt);
            }
        });
        jPanel7.add(btnNL_NH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 30));

        btnL_NH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnL_NH.setText("<<");
        btnL_NH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL_NHActionPerformed(evt);
            }
        });
        jPanel7.add(btnL_NH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 100, 30));

        btnNR_NH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNR_NH.setText(">|");
        btnNR_NH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNR_NHActionPerformed(evt);
            }
        });
        jPanel7.add(btnNR_NH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 192, 100, 30));

        btnR_NH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnR_NH.setText(">>");
        btnR_NH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_NHActionPerformed(evt);
            }
        });
        jPanel7.add(btnR_NH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, 30));

        jPanelQLduAn.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 207, -1, 230));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText("Giới tính :");
        jPanelQLduAn.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 220, -1, -1));

        jScrollPane9.setOpaque(false);

        txtNH_GhiChu.setColumns(20);
        txtNH_GhiChu.setRows(5);
        jScrollPane9.setViewportView(txtNH_GhiChu);

        jPanelQLduAn.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 270, 630, 90));

        cbbGioiTinh_NH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cbbGioiTinh_NH.setOpaque(false);
        jPanelQLduAn.add(cbbGioiTinh_NH, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 214, 230, 30));

        jTabbQLNguoiHoc.addTab("Quản Lí", jPanelQLduAn);

        jPanel9.setOpaque(false);

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Họ tên", "Giới tính", "Ngày sinh", "Sô ĐT", "Email", "Mã NV", "Ngày ĐK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblNguoiHoc);
        if (tblNguoiHoc.getColumnModel().getColumnCount() > 0) {
            tblNguoiHoc.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel25.setOpaque(false);

        txtNH_TimKiem.setOpaque(false);

        btnNH_TimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNH_TimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNH_TimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/icons8_search_25px.png"))); // NOI18N
        btnNH_TimKiem.setText("Tìm Kiếm");
        btnNH_TimKiem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnNH_TimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNH_TimKiemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(txtNH_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnNH_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtNH_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNH_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
        );

        jTabbQLNguoiHoc.addTab("Thông Tin", jPanel9);

        JPaneQuanLi.add(jTabbQLNguoiHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 1100, 500));

        jTabbQLKhoaHoc.setBackground(new java.awt.Color(255, 255, 255));
        jTabbQLKhoaHoc.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbQLKhoaHoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbQLKhoaHoc.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbQLKhoaHocAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanelQLnguoiDung.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelQLnguoiDung.setOpaque(false);
        jPanelQLnguoiDung.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Chuyên đề :");
        jPanelQLnguoiDung.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 16, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel26.setText("Học phí :");
        jPanelQLnguoiDung.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 66, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel28.setText("người tạo :");
        jPanelQLnguoiDung.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 115, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel29.setText("Ngày khai giảng :");
        jPanelQLnguoiDung.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 165, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel30.setText("Thời lượng :");
        jPanelQLnguoiDung.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 215, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel31.setText("Ngày tạo :");
        jPanelQLnguoiDung.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 265, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel32.setText("Ghi chú :");
        jPanelQLnguoiDung.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 313, -1, -1));

        txtKH_HocPhi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung.add(txtKH_HocPhi, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 59, 530, 30));

        txtKH_ngayKhaiGiang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung.add(txtKH_ngayKhaiGiang, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 159, 530, 30));

        txtKH_thoiLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung.add(txtKH_thoiLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 209, 530, 30));

        txtKH_ngayTao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung.add(txtKH_ngayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 259, 530, 30));

        jPanel2.setOpaque(false);

        btnKH_Them.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnKH_Them.setText("Thêm");
        btnKH_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKH_ThemActionPerformed(evt);
            }
        });

        btnKH_sua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnKH_sua.setText("Sửa");
        btnKH_sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKH_suaActionPerformed(evt);
            }
        });

        btnKH_xoa.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnKH_xoa.setText("Xóa");
        btnKH_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKH_xoaActionPerformed(evt);
            }
        });

        btnKH_moi.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnKH_moi.setText("Mới");
        btnKH_moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKH_moiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(btnKH_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnKH_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnKH_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnKH_moi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKH_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKH_sua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKH_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKH_moi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanelQLnguoiDung.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 393, -1, -1));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHocVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnHocVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHocVien.setText("Học Viên");
        btnHocVien.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHocVienMouseClicked(evt);
            }
        });
        jPanel1.add(btnHocVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 120, 42));

        btnNL_KH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNL_KH.setText("|<");
        btnNL_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNL_KHActionPerformed(evt);
            }
        });
        jPanel1.add(btnNL_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, 100, 30));

        btnL_KH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnL_KH.setText("<<");
        btnL_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL_KHActionPerformed(evt);
            }
        });
        jPanel1.add(btnL_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 30));

        btnNR_KH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNR_KH.setText(">|");
        btnNR_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNR_KHActionPerformed(evt);
            }
        });
        jPanel1.add(btnNR_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 100, 30));

        btnR_KH.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnR_KH.setText(">>");
        btnR_KH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_KHActionPerformed(evt);
            }
        });
        jPanel1.add(btnR_KH, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 235, 100, 30));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("In ex");
        jLabel36.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 120, 42));

        jPanelQLnguoiDung.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(835, 117, -1, 320));

        jScrollPane11.setOpaque(false);

        txtKH_ghiChu.setColumns(20);
        txtKH_ghiChu.setRows(5);
        txtKH_ghiChu.setOpaque(false);
        jScrollPane11.setViewportView(txtKH_ghiChu);

        jPanelQLnguoiDung.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 307, 530, 70));

        cbbChuyenDe.setOpaque(false);
        cbbChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChuyenDeActionPerformed(evt);
            }
        });
        jPanelQLnguoiDung.add(cbbChuyenDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 10, 250, 30));
        jPanelQLnguoiDung.add(txtKH_MaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 110, 530, 30));

        jTabbQLKhoaHoc.addTab("Quản Lí", jPanelQLnguoiDung);

        jPanel3.setOpaque(false);

        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Chuyên đề", "Thời lượng", "Học phí", "Khai giảng", "Tạo bởi", "Ngày tạo"
            }
        ));
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhoaHoc);
        if (tblKhoaHoc.getColumnModel().getColumnCount() > 0) {
            tblKhoaHoc.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        jTabbQLKhoaHoc.addTab("Thông Tin", jPanel3);

        JPaneQuanLi.add(jTabbQLKhoaHoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 1100, 500));

        jTabbQLHocVien.setBackground(new java.awt.Color(255, 255, 255));
        jTabbQLHocVien.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbQLHocVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbQLHocVien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbQLHocVienAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanelQLnguoiDung2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelQLnguoiDung2.setOpaque(false);
        jPanelQLnguoiDung2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Học viên khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel21.setOpaque(false);

        txtHV_diem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        cbbHocVien.setOpaque(false);

        btnHV_them.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnHV_them.setText("Thêm");
        btnHV_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHV_themActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(cbbHocVien, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(txtHV_diem, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnHV_them, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtHV_diem, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbbHocVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnHV_them, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanelQLnguoiDung2.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1060, 80));

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Học viên của khóa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel18.setOpaque(false);
        jPanel18.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel18AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HV", "Mã KH", "Họ và tên", "Điểm", "Xóa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblHocVien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblHocVienAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane5.setViewportView(tblHocVien);
        if (tblHocVien.getColumnModel().getColumnCount() > 0) {
            tblHocVien.getColumnModel().getColumn(4).setMinWidth(50);
            tblHocVien.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblHocVien.getColumnModel().getColumn(4).setMaxWidth(50);
        }

        rdoTatCa.setText("Tất cả");
        rdoTatCa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTatCaMouseClicked(evt);
            }
        });

        rdoDaNhap.setText("Đã nhập điểm");
        rdoDaNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDaNhapMouseClicked(evt);
            }
        });

        rdoChuaNhap.setText("Chưa nhập điểm");
        rdoChuaNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChuaNhapMouseClicked(evt);
            }
        });

        btnHV_CapNhat.setText("Cập nhật");
        btnHV_CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHV_CapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 886, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHV_CapNhat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rdoDaNhap)
                        .addComponent(rdoChuaNhap)
                        .addComponent(rdoTatCa)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoTatCa)
                .addGap(32, 32, 32)
                .addComponent(rdoDaNhap)
                .addGap(32, 32, 32)
                .addComponent(rdoChuaNhap)
                .addGap(32, 32, 32)
                .addComponent(btnHV_CapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        jPanelQLnguoiDung2.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 95, 1060, 350));

        jTabbQLHocVien.addTab("Quản Lí", jPanelQLnguoiDung2);

        JPaneQuanLi.add(jTabbQLHocVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 1100, 500));

        jTabbQLNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jTabbQLNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbQLNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbQLNhanVien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbQLNhanVienAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanelQLnguoiDung1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelQLnguoiDung1.setOpaque(false);
        jPanelQLnguoiDung1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel60.setText("Id nhân viên :");
        jPanelQLnguoiDung1.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel61.setText("Họ và tên :");
        jPanelQLnguoiDung1.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, -1, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel62.setText("Mật khẩu :");
        jPanelQLnguoiDung1.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel63.setText("Xác nhận mật khẩu :");
        jPanelQLnguoiDung1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, -1, -1));

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel64.setText("Vai trò :");
        jPanelQLnguoiDung1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));

        txtNV_ID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung1.add(txtNV_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 530, 30));

        txtNVmatKhau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung1.add(txtNVmatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 530, 30));

        txtNV_xnMatKhau.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung1.add(txtNV_xnMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 220, 530, 30));

        jPanel14.setOpaque(false);

        btnNV_Them.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNV_Them.setText("Thêm");
        btnNV_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNV_ThemActionPerformed(evt);
            }
        });

        btnNV_Sua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNV_Sua.setText("Sửa");
        btnNV_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNV_SuaActionPerformed(evt);
            }
        });

        btnNV_Xoa.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNV_Xoa.setText("Xóa");
        btnNV_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNV_XoaActionPerformed(evt);
            }
        });

        btnNV_Moi.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNV_Moi.setText("Mới");
        btnNV_Moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNV_MoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(btnNV_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNV_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNV_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNV_Moi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNV_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNV_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNV_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNV_Moi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanelQLnguoiDung1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 393, -1, -1));

        jPanel15.setOpaque(false);
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("In ex");
        jLabel68.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 42));

        btnNL_NV.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNL_NV.setText("|<");
        btnNL_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNL_NVActionPerformed(evt);
            }
        });
        jPanel15.add(btnNL_NV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 30));

        btnL_NV.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnL_NV.setText("<<");
        btnL_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL_NVActionPerformed(evt);
            }
        });
        jPanel15.add(btnL_NV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 100, 30));

        btnNR_NV.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNR_NV.setText(">|");
        btnNR_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNR_NVActionPerformed(evt);
            }
        });
        jPanel15.add(btnNR_NV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 192, 100, 30));

        btnR_NV.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnR_NV.setText(">>");
        btnR_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_NVActionPerformed(evt);
            }
        });
        jPanel15.add(btnR_NV, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, 30));

        jPanelQLnguoiDung1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(825, 207, -1, 230));

        rdoTruongPhong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoTruongPhong.setText("Trưởng phòng");
        rdoTruongPhong.setOpaque(false);
        jPanelQLnguoiDung1.add(rdoTruongPhong, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 280, -1, -1));

        rdoNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.setOpaque(false);
        jPanelQLnguoiDung1.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, -1, -1));

        txtNVhoTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLnguoiDung1.add(txtNVhoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 530, 30));

        jTabbQLNhanVien.addTab("Quản Lí", jPanelQLnguoiDung1);

        jPanel17.setOpaque(false);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên nhân viên", "Mật khẩu", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setMinWidth(70);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        jTabbQLNhanVien.addTab("Thông Tin", jPanel17);

        JPaneQuanLi.add(jTabbQLNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 1100, 500));

        jTabbQLChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        jTabbQLChuyenDe.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbQLChuyenDe.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTabbQLChuyenDe.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTabbQLChuyenDeAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanelQLsanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelQLsanPham.setOpaque(false);
        jPanelQLsanPham.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel50.setText("ID chuyên đề :");
        jPanelQLsanPham.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 64, -1, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel51.setText("Tên chuyên đề :");
        jPanelQLsanPham.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 113, -1, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel52.setText("Thời lượng :");
        jPanelQLsanPham.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 163, -1, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel53.setText("Mô tả :");
        jPanelQLsanPham.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 263, -1, -1));

        txtCD_ID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLsanPham.add(txtCD_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 57, 627, 30));

        txtCD_Ten_CD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLsanPham.add(txtCD_Ten_CD, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 107, 627, 30));

        txtCD_ThoiLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLsanPham.add(txtCD_ThoiLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 157, 627, 30));

        txtCD_hocPhi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanelQLsanPham.add(txtCD_hocPhi, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 207, 627, 30));

        jPanel10.setOpaque(false);

        btnCD_Them.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnCD_Them.setText("Thêm");
        btnCD_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCD_ThemActionPerformed(evt);
            }
        });

        btnCD_Sua.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnCD_Sua.setText("Sửa");
        btnCD_Sua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCD_SuaActionPerformed(evt);
            }
        });

        btnCD_Xoa.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnCD_Xoa.setText("Xóa");
        btnCD_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCD_XoaActionPerformed(evt);
            }
        });

        btnCD_Moi.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnCD_Moi.setText("Mới");
        btnCD_Moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCD_MoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(btnCD_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnCD_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnCD_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnCD_Moi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCD_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCD_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCD_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCD_Moi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanelQLsanPham.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 393, -1, -1));

        jPanel11.setOpaque(false);
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDA_inEx1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDA_inEx1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDA_inEx1.setText("In ex");
        lblDA_inEx1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.add(lblDA_inEx1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 42));

        btnNL_CD.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNL_CD.setText("|<");
        btnNL_CD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNL_CDActionPerformed(evt);
            }
        });
        jPanel11.add(btnNL_CD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 30));

        btnL_CD.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnL_CD.setText("<<");
        btnL_CD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnL_CDActionPerformed(evt);
            }
        });
        jPanel11.add(btnL_CD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 100, 30));

        btnNR_CD.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNR_CD.setText(">|");
        btnNR_CD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNR_CDActionPerformed(evt);
            }
        });
        jPanel11.add(btnNR_CD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 192, 100, 30));

        btnR_CD.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnR_CD.setText(">>");
        btnR_CD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnR_CDActionPerformed(evt);
            }
        });
        jPanel11.add(btnR_CD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 100, 30));

        jPanelQLsanPham.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(865, 207, -1, 230));

        jPanel12.setOpaque(false);

        lblAnh_CD.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAnh_CD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh_CD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblAnh_CD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnh_CDMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblAnh_CD, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblAnh_CD, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
        );

        jPanelQLsanPham.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(839, 8, 170, 180));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel55.setText("Học phí :");
        jPanelQLsanPham.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 213, -1, -1));

        jScrollPane10.setOpaque(false);

        txtCD_mota.setColumns(20);
        txtCD_mota.setRows(5);
        txtCD_mota.setOpaque(false);
        jScrollPane10.setViewportView(txtCD_mota);

        jPanelQLsanPham.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 257, 627, 100));

        jTabbQLChuyenDe.addTab("Quản Lí", jPanelQLsanPham);

        jPanel13.setOpaque(false);

        tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên chuyên đề", "Học phí", "Thời lượng", "Hình ảnh"
            }
        ));
        tblChuyenDe.setOpaque(false);
        tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuyenDeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChuyenDe);
        if (tblChuyenDe.getColumnModel().getColumnCount() > 0) {
            tblChuyenDe.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1091, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        jTabbQLChuyenDe.addTab("Thông Tin", jPanel13);

        JPaneQuanLi.add(jTabbQLChuyenDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 1100, 500));

        backg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        backg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/6.png"))); // NOI18N
        JPaneQuanLi.add(backg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1240, 520));

        jPaneForm.add(JPaneQuanLi);
        JPaneQuanLi.setBounds(40, 0, 1240, 590);

        JPaneHeThong.setBackground(new java.awt.Color(255, 255, 255));
        JPaneHeThong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/Log out.png"))); // NOI18N
        lblLogout.setText("Logout");
        lblLogout.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblLogout.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });
        JPaneHeThong.add(lblLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 90, 120, 40));

        lblThoat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/Right.png"))); // NOI18N
        lblThoat.setText("Thoát");
        lblThoat.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblThoat.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThoatMouseClicked(evt);
            }
        });
        JPaneHeThong.add(lblThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 90, 120, 40));

        lblGioiThieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGioiThieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/Conference.png"))); // NOI18N
        lblGioiThieu.setText("Giới Thiệu");
        lblGioiThieu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblGioiThieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGioiThieuMouseClicked(evt);
            }
        });
        JPaneHeThong.add(lblGioiThieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 120, 40));

        lblTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/User.png"))); // NOI18N
        lblTaiKhoan.setText("Tài Khoản");
        lblTaiKhoan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTaiKhoanMouseClicked(evt);
            }
        });
        JPaneHeThong.add(lblTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 120, 40));

        JPaneGioiThieu.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        JPaneGioiThieu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JPaneGioiThieu.setOpaque(false);
        JPaneGioiThieu.setLayout(null);
        JPaneGioiThieu.add(jSeparator5);
        jSeparator5.setBounds(340, 80, 360, 10);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel25.setText("Giới thiệu về công ty");
        JPaneGioiThieu.add(jLabel25);
        jLabel25.setBounds(420, 50, 200, 22);

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/logo.png"))); // NOI18N
        JPaneGioiThieu.add(jLabel65);
        jLabel65.setBounds(540, 90, 490, 300);

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel73.setText("Polypro là dự án mẫu. Mục tiêu chính là huấn luyện sinh viên quy trình thực hiện dự án.");
        JPaneGioiThieu.add(jLabel73);
        jLabel73.setBounds(40, 130, 480, 15);

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel75.setText("Mục tiêu của dự án này là để rèn luyện kĩ năng IO ( CDIO ) tức không yêu cầu sinh viên phải");
        JPaneGioiThieu.add(jLabel75);
        jLabel75.setBounds(40, 160, 509, 20);

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel76.setText("thu thập phân tích mà chỉ thực hiện và vận hành một phần mềm chuẩn bị cho các dự án");
        JPaneGioiThieu.add(jLabel76);
        jLabel76.setBounds(40, 190, 489, 15);

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel77.setText("sau này. Các kĩ năng CD trong CDIOn sẽ được huẩn luyện ở dự án 1 và dự án 2");
        JPaneGioiThieu.add(jLabel77);
        jLabel77.setBounds(40, 220, 436, 15);

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel78.setText("Yêu cầu về môi trường :");
        JPaneGioiThieu.add(jLabel78);
        jLabel78.setBounds(40, 260, 135, 15);

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel79.setText("1. Hệ điều hành bất kì");
        JPaneGioiThieu.add(jLabel79);
        jLabel79.setBounds(40, 280, 180, 15);

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel80.setText("2. IDK 1.8 trở lên");
        JPaneGioiThieu.add(jLabel80);
        jLabel80.setBounds(40, 300, 96, 15);

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel81.setText("3. SQL server 2008 trở lên");
        JPaneGioiThieu.add(jLabel81);
        jLabel81.setBounds(40, 320, 146, 15);

        JPaneHeThong.add(JPaneGioiThieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 1050, 400));
        JPaneHeThong.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 260, 10));
        JPaneHeThong.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 140, 260, -1));

        JPaneTaiKhoan.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        JPaneTaiKhoan.setOpaque(false);
        JPaneTaiKhoan.setLayout(null);
        JPaneTaiKhoan.add(jSeparator3);
        jSeparator3.setBounds(190, 80, 400, 10);

        lblNameTK.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblNameTK.setForeground(new java.awt.Color(255, 0, 0));
        lblNameTK.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblNameTKAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        JPaneTaiKhoan.add(lblNameTK);
        lblNameTK.setBounds(280, 50, 310, 40);

        lblShow1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShow1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/show.png"))); // NOI18N
        lblShow1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShow1MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblShow1);
        lblShow1.setBounds(550, 130, 40, 20);

        lblHide1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHide1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/hide.png"))); // NOI18N
        lblHide1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHide1MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblHide1);
        lblHide1.setBounds(550, 130, 40, 20);

        txtPassCu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassCu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassCuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassCuFocusLost(evt);
            }
        });
        JPaneTaiKhoan.add(txtPassCu);
        txtPassCu.setBounds(190, 120, 400, 40);

        lblShow2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShow2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/show.png"))); // NOI18N
        lblShow2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShow2MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblShow2);
        lblShow2.setBounds(550, 210, 40, 20);

        lblHide2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHide2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/hide.png"))); // NOI18N
        lblHide2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHide2MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblHide2);
        lblHide2.setBounds(550, 210, 40, 20);

        txtPassMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassMoi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassMoiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassMoiFocusLost(evt);
            }
        });
        JPaneTaiKhoan.add(txtPassMoi);
        txtPassMoi.setBounds(190, 200, 400, 40);

        lblShow3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShow3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/show.png"))); // NOI18N
        lblShow3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShow3MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblShow3);
        lblShow3.setBounds(550, 290, 40, 20);

        lblHide3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHide3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/hide.png"))); // NOI18N
        lblHide3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHide3MouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblHide3);
        lblHide3.setBounds(550, 290, 40, 20);

        txtPassXN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPassXN.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassXNFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassXNFocusLost(evt);
            }
        });
        JPaneTaiKhoan.add(txtPassXN);
        txtPassXN.setBounds(190, 280, 400, 40);

        lblThemTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThemTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThemTK.setText("Đổi MK");
        lblThemTK.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblThemTK.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblThemTKAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblThemTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThemTKMouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblThemTK);
        lblThemTK.setBounds(730, 240, 120, 40);

        lblNewTK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNewTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewTK.setText("NEW");
        lblNewTK.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblNewTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNewTKMouseClicked(evt);
            }
        });
        JPaneTaiKhoan.add(lblNewTK);
        lblNewTK.setBounds(730, 150, 120, 40);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("UserName :");
        JPaneTaiKhoan.add(jLabel3);
        jLabel3.setBounds(190, 60, 80, 19);

        JPaneHeThong.add(JPaneTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 1050, 400));

        JPaneAbout.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        JPaneAbout.setOpaque(false);
        JPaneAbout.setLayout(null);
        JPaneAbout.add(jSeparator4);
        jSeparator4.setBounds(340, 80, 360, 10);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Thông Tin Phần Mềm");
        JPaneAbout.add(jLabel10);
        jLabel10.setBounds(420, 50, 200, 22);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/logo.png"))); // NOI18N
        JPaneAbout.add(jLabel11);
        jLabel11.setBounds(50, 100, 500, 300);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Phần mềm : Quản lí thư viện");
        JPaneAbout.add(jLabel12);
        jLabel12.setBounds(600, 110, 220, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Version : 1.0\n\n\n");
        JPaneAbout.add(jLabel13);
        jLabel13.setBounds(600, 140, 90, 15);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("+ Phân tích ");
        JPaneAbout.add(jLabel14);
        jLabel14.setBounds(610, 340, 190, 15);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Nhà Phát triển : Nguyễn Văn Tân\n");
        JPaneAbout.add(jLabel15);
        jLabel15.setBounds(600, 200, 190, 15);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Cty sở hữu : FPT Software");
        JPaneAbout.add(jLabel16);
        jLabel16.setBounds(600, 180, 270, 15);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Nhà Phát triển : Nguyễn Văn Tân\n");
        JPaneAbout.add(jLabel17);
        jLabel17.setBounds(600, 160, 190, 15);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("+ Quản lí thông tin chuyên đề");
        JPaneAbout.add(jLabel18);
        jLabel18.setBounds(610, 300, 190, 15);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("+ Quản lý thông tin học viên");
        JPaneAbout.add(jLabel19);
        jLabel19.setBounds(610, 260, 190, 15);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("+ Quản lý tài khoản người dùng");
        JPaneAbout.add(jLabel20);
        jLabel20.setBounds(610, 240, 190, 15);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("+ Quản lý thông tin người học");
        JPaneAbout.add(jLabel21);
        jLabel21.setBounds(610, 280, 190, 15);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("- Các chức năng chính :");
        JPaneAbout.add(jLabel22);
        jLabel22.setBounds(600, 220, 190, 15);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("+ Thống kê ");
        JPaneAbout.add(jLabel23);
        jLabel23.setBounds(610, 360, 190, 15);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("+ Quản lí thông tin khóa học");
        JPaneAbout.add(jLabel24);
        jLabel24.setBounds(610, 320, 190, 15);

        JPaneHeThong.add(JPaneAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 1050, 400));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee333.png"))); // NOI18N
        jLabel2.setText("HỆ THỐNG");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JPaneHeThong.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 70));

        backk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/6.png"))); // NOI18N
        JPaneHeThong.add(backk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1240, 520));

        jPaneForm.add(JPaneHeThong);
        JPaneHeThong.setBounds(40, 0, 1240, 590);

        JPaneThoat.setBackground(new java.awt.Color(255, 255, 255));
        JPaneThoat.setLayout(null);

        right1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        right1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panRight.png"))); // NOI18N
        JPaneThoat.add(right1);
        right1.setBounds(459, -10, 670, 610);

        top1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        top1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panTopp.png"))); // NOI18N
        JPaneThoat.add(top1);
        top1.setBounds(0, 0, 982, 261);

        left1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        left1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panLeft.png"))); // NOI18N
        JPaneThoat.add(left1);
        left1.setBounds(0, 0, 643, 590);

        bot1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bot1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/panBot.png"))); // NOI18N
        JPaneThoat.add(bot1);
        bot1.setBounds(126, 330, 1010, 270);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("Bạn muốn kết thúc chương trình ?");
        JPaneThoat.add(jLabel8);
        jLabel8.setBounds(400, 220, 330, 45);

        btnCan1.setText("Không");
        btnCan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCan1ActionPerformed(evt);
            }
        });
        JPaneThoat.add(btnCan1);
        btnCan1.setBounds(630, 310, 90, 34);

        btnYes1.setText("Đồng ý");
        btnYes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYes1ActionPerformed(evt);
            }
        });
        JPaneThoat.add(btnYes1);
        btnYes1.setBounds(390, 310, 90, 34);

        jPaneForm.add(JPaneThoat);
        JPaneThoat.setBounds(160, 0, 1120, 590);

        JPaneLoogin.setBackground(new java.awt.Color(255, 255, 255));
        JPaneLoogin.setPreferredSize(new java.awt.Dimension(1120, 590));
        JPaneLoogin.setLayout(null);

        txtUserName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserNameFocusLost(evt);
            }
        });
        txtUserName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserNameMouseClicked(evt);
            }
        });
        JPaneLoogin.add(txtUserName);
        txtUserName.setBounds(440, 220, 250, 40);

        lblEyyy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEyyy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/show.png"))); // NOI18N
        lblEyyy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEyyyMouseClicked(evt);
            }
        });
        JPaneLoogin.add(lblEyyy);
        lblEyyy.setBounds(650, 300, 40, 20);

        lblEye.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/hide.png"))); // NOI18N
        lblEye.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lblEyeAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        lblEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEyeMouseClicked(evt);
            }
        });
        JPaneLoogin.add(lblEye);
        lblEye.setBounds(650, 300, 40, 20);

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassFocusLost(evt);
            }
        });
        txtPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPassMouseClicked(evt);
            }
        });
        JPaneLoogin.add(txtPass);
        txtPass.setBounds(440, 290, 250, 40);

        chkGhiNho.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkGhiNho.setForeground(new java.awt.Color(255, 255, 255));
        chkGhiNho.setText("Ghi nhớ thông tin");
        chkGhiNho.setOpaque(false);
        JPaneLoogin.add(chkGhiNho);
        chkGhiNho.setBounds(440, 340, 130, 23);

        btnLoginOK.setText("Login");
        btnLoginOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginOKActionPerformed(evt);
            }
        });
        JPaneLoogin.add(btnLoginOK);
        btnLoginOK.setBounds(600, 390, 90, 30);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        JPaneLoogin.add(btnCancel);
        btnCancel.setBounds(443, 390, 90, 30);

        lblLogin.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee2.png"))); // NOI18N
        lblLogin.setText("LOGIN");
        lblLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JPaneLoogin.add(lblLogin);
        lblLogin.setBounds(0, 0, 1120, 70);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/LoginBackgXX.png"))); // NOI18N
        jLabel4.setOpaque(true);
        JPaneLoogin.add(jLabel4);
        jLabel4.setBounds(230, 100, 700, 480);

        jPaneForm.add(JPaneLoogin);
        JPaneLoogin.setBounds(160, 0, 1130, 590);

        lbl_BackG_Home.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_BackG_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_blue1.png"))); // NOI18N
        jPaneForm.add(lbl_BackG_Home);
        lbl_BackG_Home.setBounds(40, 0, 120, 70);

        JPaneLogout.setBackground(new java.awt.Color(255, 255, 255));
        JPaneLogout.setPreferredSize(new java.awt.Dimension(1120, 590));
        JPaneLogout.setLayout(null);

        txtUserName1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtUserName1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserName1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserName1FocusLost(evt);
            }
        });
        JPaneLogout.add(txtUserName1);
        txtUserName1.setBounds(440, 220, 250, 40);

        lblShowLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblShowLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/show.png"))); // NOI18N
        lblShowLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblShowLogoutMouseClicked(evt);
            }
        });
        JPaneLogout.add(lblShowLogout);
        lblShowLogout.setBounds(650, 300, 40, 20);

        lblHideLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHideLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/icon/hide.png"))); // NOI18N
        lblHideLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHideLogoutMouseClicked(evt);
            }
        });
        JPaneLogout.add(lblHideLogout);
        lblHideLogout.setBounds(650, 300, 40, 20);

        txtPass1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPass1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPass1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass1FocusLost(evt);
            }
        });
        JPaneLogout.add(txtPass1);
        txtPass1.setBounds(440, 290, 250, 40);

        chkGhiNho1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkGhiNho1.setForeground(new java.awt.Color(255, 255, 255));
        chkGhiNho1.setText("Ghi nhớ thông tin");
        chkGhiNho1.setOpaque(false);
        JPaneLogout.add(chkGhiNho1);
        chkGhiNho1.setBounds(440, 340, 130, 23);

        btnLoginOK1.setText("Login");
        btnLoginOK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginOK1ActionPerformed(evt);
            }
        });
        JPaneLogout.add(btnLoginOK1);
        btnLoginOK1.setBounds(600, 390, 90, 30);

        btnEx.setText("Exit");
        btnEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExActionPerformed(evt);
            }
        });
        JPaneLogout.add(btnEx);
        btnEx.setBounds(443, 390, 90, 30);

        lblLogin1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        lblLogin1.setForeground(new java.awt.Color(255, 255, 255));
        lblLogin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/menu_bluee333.png"))); // NOI18N
        lblLogin1.setText("LOGIN");
        lblLogin1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        JPaneLogout.add(lblLogin1);
        lblLogin1.setBounds(-120, 0, 1240, 70);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imager/hinh/LoginBackgXX.png"))); // NOI18N
        jLabel9.setOpaque(true);
        JPaneLogout.add(jLabel9);
        jLabel9.setBounds(230, 100, 700, 480);

        jPaneForm.add(JPaneLogout);
        JPaneLogout.setBounds(160, 0, 1130, 590);

        jPanel4.add(jPaneForm);
        jPaneForm.setBounds(0, 0, 1280, 590);

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 590));

        setSize(new java.awt.Dimension(1286, 620));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPaneFormAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPaneFormAncestorAdded

        // TODO add your handling code here:
    }//GEN-LAST:event_jPaneFormAncestorAdded

    private void txtUserNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNameFocusLost
        if (txtUserName.getText().isEmpty()) {
            txtUserName.setForeground(Color.GRAY);
            txtUserName.setText("Nhập tên đăng nhập");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameFocusLost

    private void txtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusLost
        if (txtPass.getText().isEmpty()) {
            txtPass.setForeground(Color.GRAY);
            txtPass.setText("Nhập mật khẩu");
            txtPass.setEchoChar((char) 0);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassFocusLost

    private void txtUserNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserNameFocusGained
        if (txtUserName.getText().equals("Nhập tên đăng nhập")) {
            txtUserName.setText("");
            txtUserName.setForeground(Color.BLACK);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameFocusGained

    private void txtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusGained
        if (txtPass.getText().equals("Nhập mật khẩu")) {
            txtPass.setText("");
            txtPass.setForeground(Color.BLACK);
            txtPass.setEchoChar('*');    // chuyển mật khẩu sang dạng *
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassFocusGained

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        JPaneHome.setVisible(true);
        JPaneLoogin.setVisible(false);
        btnLogin.setSelected(false);

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnLoginOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginOKActionPerformed
        // TODO add your handling code here:
        UserLogin();
    }//GEN-LAST:event_btnLoginOKActionPerformed

    private void jPaneMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPaneMenuMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_jPaneMenuMouseClicked

    private void backgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_backgroundMouseClicked

    private void JPaneMenuLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneMenuLoginMouseClicked

        // jPanel2.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_JPaneMenuLoginMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked

        // TODO add your handling code here:
        btnExit.setSelected(true);
        btnLogin.setSelected(false);
        jPaneExit.setVisible(true);
        JPaneLoogin.setVisible(false);
        if (JPaneHome.isVisible()) {
            JPaneHome.setVisible(false);
        }

        this.ExitPane();

    }//GEN-LAST:event_btnExitMouseClicked

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked

        btnExit.setSelected(false);
        btnLogin.setSelected(true);
        JPaneLoogin.setVisible(true);
        jPaneExit.setVisible(false);
        JPaneHome.setVisible(false);
        //  txtUserName.requestFocus();
    }//GEN-LAST:event_btnLoginMouseClicked

    private void JPaneMenuLogin2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPaneMenuLogin2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_JPaneMenuLogin2MouseClicked

    private void btnHeThongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHeThongMouseClicked

    }//GEN-LAST:event_btnHeThongMouseClicked

    private void btnQuanLiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuanLiMouseClicked

    }//GEN-LAST:event_btnQuanLiMouseClicked

    private void btnThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseClicked

    }//GEN-LAST:event_btnThongKeMouseClicked

    private void btnPhanTichMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhanTichMouseClicked

    }//GEN-LAST:event_btnPhanTichMouseClicked

    private void lblGifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGifMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_lblGifMouseClicked

    private void lblGifMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGifMousePressed

        this.MenuThu();
    }//GEN-LAST:event_lblGifMousePressed

    private void lblGachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGachMousePressed
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    for (int a = 0; a <= 20; a++) {
                        Thread.sleep(30);
                        if (a == 1) {
                            jPaneMenu.setSize(46, 590);
                            lblGach.setSize(0, 0);
                            lblGif.setSize(160, 50);
                            lblBackG.setVisible(false);
                        }
                        if (a == 2) {
                            jPaneMenu.setSize(52, 590);
                        }
                        if (a == 3) {
                            jPaneMenu.setSize(58, 590);
                        }
                        if (a == 4) {
                            jPaneMenu.setSize(64, 590);
                        }
                        if (a == 5) {
                            jPaneMenu.setSize(70, 590);
                        }
                        if (a == 6) {
                            jPaneMenu.setSize(76, 590);
                        }
                        if (a == 7) {
                            jPaneMenu.setSize(82, 590);
                        }
                        if (a == 8) {
                            jPaneMenu.setSize(88, 590);
                        }
                        if (a == 9) {
                            jPaneMenu.setSize(94, 590);
                        }
                        if (a == 10) {
                            jPaneMenu.setSize(100, 590);

                        }
                        if (a == 11) {
                            jPaneMenu.setSize(106, 590);

                        }
                        if (a == 12) {
                            jPaneMenu.setSize(112, 590);

                        }
                        if (a == 13) {
                            jPaneMenu.setSize(118, 590);

                        }
                        if (a == 14) {
                            jPaneMenu.setSize(124, 590);

                        }
                        if (a == 15) {
                            jPaneMenu.setSize(130, 590);

                        }
                        if (a == 16) {
                            jPaneMenu.setSize(136, 590);

                        }
                        if (a == 17) {
                            jPaneMenu.setSize(142, 590);

                        }
                        if (a == 18) {
                            jPaneMenu.setSize(148, 590);

                        }
                        if (a == 19) {
                            jPaneMenu.setSize(154, 590);

                        }
                        if (a == 20) {
                            jPaneMenu.setSize(160, 590);

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        th.start();
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGachMousePressed

    private void btnCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanActionPerformed
        // TODO add your handling code here:
        jPaneExit.setVisible(false);
        JPaneHome.setVisible(true);
        btnExit.setSelected(false);
//        if(JPaneHeThong.isVisible()){
//        JPaneHeThong.setVisible(true);
//        }
    }//GEN-LAST:event_btnCanActionPerformed

    private void btnYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYesActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnYesActionPerformed

    private void btnExitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExitMousePressed

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked

    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnCan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCan1ActionPerformed
        JPaneHeThong.setVisible(true);
        JPaneThoat.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCan1ActionPerformed

    private void btnYes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYes1ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnYes1ActionPerformed

    private void lblThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseClicked
        JPaneHeThong.setVisible(false);
        JPaneThoat.setVisible(true);

        this.ThoatPane();
        // TODO add your handling code here:

    }//GEN-LAST:event_lblThoatMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked

        // JPaneLogout.setVisible(true);
        JPaneLoogin.setVisible(true);
        JPaneHeThong.setVisible(false);
        btnHome.setEnabled(false);
        btnThongKe.setEnabled(false);
        btnPhanTich.setEnabled(false);
        btnQuanLi.setEnabled(false);
        btnHeThong.setEnabled(false);
        JPaneMenuLogin2.setEnabled(false);
        JPaneMenuLogin2.setVisible(true);
        lblBackG.setVisible(false);
        lblGach.setVisible(false);
        lblGif.setVisible(false);

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    for (int a = 0; a <= 1; a++) {
                        Thread.sleep(0);
                        if (a == 1) {
                            jPaneMenu.setSize(160, 590);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        th.start();
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void txtUserName1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserName1FocusGained
        // TODO add your handling code here:
        if (txtUserName1.getText().equals("Nhập tên đăng nhập")) {
            txtUserName1.setText("");
            txtUserName1.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtUserName1FocusGained

    private void txtUserName1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserName1FocusLost
        // TODO add your handling code here:
        if (txtUserName1.getText().isEmpty()) {
            txtUserName1.setForeground(Color.GRAY);
            txtUserName1.setText("Nhập tên đăng nhập");
        }
    }//GEN-LAST:event_txtUserName1FocusLost

    private void txtPass1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass1FocusGained
        if (txtPass1.getText().equals("Nhập mật khẩu")) {
            txtPass1.setText("");
            txtPass1.setForeground(Color.BLACK);
            txtPass1.setEchoChar('*');
        }
    }//GEN-LAST:event_txtPass1FocusGained

    private void txtPass1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass1FocusLost
        if (txtPass1.getText().isEmpty()) {
            setForeground(Color.GRAY);
            txtPass1.setText("Nhập mật khẩu");
            txtPass1.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_txtPass1FocusLost

    private void btnLoginOK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginOK1ActionPerformed
        UserLogin();
    }//GEN-LAST:event_btnLoginOK1ActionPerformed

    private void btnExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExActionPerformed
        System.exit(0);
//        JPaneLogout.setVisible(false);
//        JPaneHeThong.setVisible(true);
    }//GEN-LAST:event_btnExActionPerformed

    private void lblTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTaiKhoanMouseClicked

        JPaneTaiKhoan.setVisible(true);
        lblNameTK.setText(TenNgDung);
        if (JPaneGioiThieu.isVisible()) {
            JPaneGioiThieu.setVisible(false);
        }
        if (JPaneAbout.isVisible()) {
            JPaneAbout.setVisible(false);
        }
    }//GEN-LAST:event_lblTaiKhoanMouseClicked

    private void lblThemTKAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblThemTKAncestorAdded

        // TODO add your handling code here:
    }//GEN-LAST:event_lblThemTKAncestorAdded

    private void lblThemTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThemTKMouseClicked
        if (txtPassCu.getPassword().equals("") || txtPassMoi.getPassword().equals("") || txtPassXN.getPassword().equals("")) {
            JOptionPane.showMessageDialog(this, " Vui lòng nhập đủ thông tin !");
        } else {
            this.DoiMK();
        }
    }//GEN-LAST:event_lblThemTKMouseClicked

    private void lblNameTKAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblNameTKAncestorAdded
        lblNameTK.setText(TenNgDung);
        // TODO add your handling code here:
    }//GEN-LAST:event_lblNameTKAncestorAdded

    private void txtUserNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserNameMouseClicked
//        txtUserName.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameMouseClicked

    private void txtPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPassMouseClicked
        //     txtPass.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassMouseClicked

    private void lblNewTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewTKMouseClicked
        txtPassCu.setText("");
        txtPassMoi.setText("");
        txtPassXN.setText("");
    }//GEN-LAST:event_lblNewTKMouseClicked

    private void lblEyyyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyyyMouseClicked
        lblEye.setVisible(true);
        lblEyyy.setVisible(false);
        txtPass.setEchoChar((char) 0);
    }//GEN-LAST:event_lblEyyyMouseClicked

    private void lblEyeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lblEyeAncestorAdded

    }//GEN-LAST:event_lblEyeAncestorAdded

    private void lblEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEyeMouseClicked
        lblEye.setVisible(false);
        lblEyyy.setVisible(true);
        txtPass.setEchoChar('*');
    }//GEN-LAST:event_lblEyeMouseClicked

    private void txtPassCuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassCuFocusLost
        if (txtPassCu.getText().isEmpty()) {
            txtPassCu.setForeground(Color.GRAY);
            txtPassCu.setText("Nhập mật khẩu cũ");
            txtPassCu.setEchoChar((char) 0);
        }

    }//GEN-LAST:event_txtPassCuFocusLost

    private void txtPassMoiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassMoiFocusLost
        if (txtPassMoi.getText().isEmpty()) {
            txtPassMoi.setForeground(Color.GRAY);
            txtPassMoi.setText("Nhập mật khẩu mới");
            txtPassMoi.setEchoChar((char) 0);
        }

    }//GEN-LAST:event_txtPassMoiFocusLost

    private void txtPassXNFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassXNFocusLost
        if (txtPassXN.getText().isEmpty()) {
            txtPassXN.setForeground(Color.GRAY);
            txtPassXN.setText("Xác nhận mật khẩu mới");
            txtPassXN.setEchoChar((char) 0);
        }

    }//GEN-LAST:event_txtPassXNFocusLost

    private void txtPassCuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassCuFocusGained
        if (txtPassCu.getText().equals("Nhập mật khẩu cũ")) {
            txtPassCu.setText("");
            txtPassCu.setForeground(Color.BLACK);
            txtPassCu.setEchoChar('*');  // chuyển mật khẩu từ String sang *
        }

    }//GEN-LAST:event_txtPassCuFocusGained

    private void txtPassMoiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassMoiFocusGained
        if (txtPassMoi.getText().equals("Nhập mật khẩu mới")) {
            txtPassMoi.setText("");
            txtPassMoi.setForeground(Color.BLACK);
            txtPassMoi.setEchoChar('*');
        }
    }//GEN-LAST:event_txtPassMoiFocusGained

    private void txtPassXNFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassXNFocusGained
        if (txtPassXN.getText().equals("Xác nhận mật khẩu mới")) {
            txtPassXN.setText("");
            txtPassXN.setForeground(Color.BLACK);
            txtPassXN.setEchoChar('*');
        }

    }//GEN-LAST:event_txtPassXNFocusGained

    private void lblShow1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShow1MouseClicked
        lblShow1.setVisible(false);
        lblHide1.setVisible(true);
        txtPassCu.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShow1MouseClicked

    private void lblHide1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHide1MouseClicked
        lblHide1.setVisible(false);
        lblShow1.setVisible(true);
        txtPassCu.setEchoChar('*');
    }//GEN-LAST:event_lblHide1MouseClicked

    private void lblShow2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShow2MouseClicked
        lblShow2.setVisible(false);
        lblHide2.setVisible(true);
        txtPassMoi.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShow2MouseClicked

    private void lblHide2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHide2MouseClicked
        lblHide2.setVisible(false);
        lblShow2.setVisible(true);
        txtPassMoi.setEchoChar('*');
    }//GEN-LAST:event_lblHide2MouseClicked

    private void lblShow3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShow3MouseClicked
        lblShow3.setVisible(false);
        lblHide3.setVisible(true);
        txtPassXN.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShow3MouseClicked

    private void lblHide3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHide3MouseClicked
        lblShow3.setVisible(true);
        lblHide3.setVisible(false);
        txtPassXN.setEchoChar('*');
    }//GEN-LAST:event_lblHide3MouseClicked

    private void lblShowLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowLogoutMouseClicked
        lblShowLogout.setVisible(false);
        lblHideLogout.setVisible(true);
        txtPass1.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShowLogoutMouseClicked

    private void lblHideLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideLogoutMouseClicked
        lblHideLogout.setVisible(false);
        lblShowLogout.setVisible(true);
        txtPass1.setEchoChar('*');
    }//GEN-LAST:event_lblHideLogoutMouseClicked

    private void lblGioiThieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGioiThieuMouseClicked
        JPaneGioiThieu.setVisible(true);
        if (JPaneAbout.isVisible()) {
            JPaneAbout.setVisible(false);
        }
        if (JPaneTaiKhoan.isVisible()) {
            JPaneTaiKhoan.setVisible(false);
        }

    }//GEN-LAST:event_lblGioiThieuMouseClicked

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        lbl_BackG_Home.setVisible(true);
        btnHome.setSelected(true);
        btnHeThong.setSelected(false);
        btnPhanTich.setSelected(false);
        btnThongKe.setSelected(false);
        btnQuanLi.setSelected(false);
        JPaneMenuLogin2.setVisible(true);
        JPaneHome.setVisible(true);

        if (JPanePhanTich.isVisible()) {
            JPanePhanTich.setVisible(false);
        }
        if (JPaneThongKe.isVisible()) {
            JPaneThongKe.setVisible(false);
        }
        if (JPaneQuanLi.isVisible()) {
            JPaneQuanLi.setVisible(false);
        }
        if (JPaneHeThong.isVisible()) {
            JPaneHeThong.setVisible(false);
        }

//        if (lblGach.isVisible() && lblGif.isVisible()) {
//            lblGach.setVisible(false);
//            lblGif.setVisible(false);
//        }
        lblGach.setVisible(true);
        lblGif.setVisible(true);
        this.MenuThu();

    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnPhanTichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanTichActionPerformed
        //   if (role == 2 || role == 3 || role == 4) {
        if (role == false) {
            JPanePhanTich.setVisible(false);
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập !", "error", JOptionPane.ERROR_MESSAGE);
        } else {
            btnPhanTich.setSelected(true);
            btnHome.setSelected(false);
            btnHeThong.setSelected(false);
            btnThongKe.setSelected(false);
            btnQuanLi.setSelected(false);

            JPanePhanTich.setVisible(true);
            if (JPaneHome.isVisible()) {
                JPaneHome.setVisible(false);
            }
            if (JPaneThongKe.isVisible()) {
                JPaneThongKe.setVisible(false);
            }
            if (JPaneQuanLi.isVisible()) {
                JPaneQuanLi.setVisible(false);
            }
            if (JPaneHeThong.isVisible()) {
                JPaneHeThong.setVisible(false);
            }

            lblGach.setVisible(true);
            lblGif.setVisible(true);
            this.MenuThu();
        }
    }//GEN-LAST:event_btnPhanTichActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed

//         this.add(JPaneThongKe, new Integer(20), 0);
//         this.add(JPaneMenuLogin2, new Integer(25), 0);
//        if (role == 2 || role == 3 || role == 4) {
        if (role == false) {
            JPaneThongKe.setVisible(false);
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập !", "error", JOptionPane.ERROR_MESSAGE);
        } else {
            btnThongKe.setSelected(true);
            btnHome.setSelected(false);
            btnHeThong.setSelected(false);
            btnPhanTich.setSelected(false);
            btnQuanLi.setSelected(false);

            JPaneThongKe.setVisible(true);
            if (JPaneHome.isVisible()) {
                JPaneHome.setVisible(false);
            }
            if (JPanePhanTich.isVisible()) {
                JPanePhanTich.setVisible(false);
            }

            if (JPaneQuanLi.isVisible()) {
                JPaneQuanLi.setVisible(false);
            }

            if (JPaneHeThong.isVisible()) {
                JPaneHeThong.setVisible(false);
            }

            lblGach.setVisible(true);
            lblGif.setVisible(true);
            this.MenuThu();
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnQuanLiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLiActionPerformed
//        if (role == 2 || role == 3 || role == 4) {
        if (role == false) {
            btnNhanVien.setEnabled(false);
        }

        btnQuanLi.setSelected(true);
        btnThongKe.setSelected(false);
        btnHome.setSelected(false);
        btnHeThong.setSelected(false);
        btnPhanTich.setSelected(false);

        JPaneQuanLi.setVisible(true);
        if (JPaneThongKe.isVisible()) {
            JPaneThongKe.setVisible(false);
        }
        if (JPaneHome.isVisible()) {
            JPaneHome.setVisible(false);
        }

        if (JPanePhanTich.isVisible()) {
            JPanePhanTich.setVisible(false);
        }
        if (JPaneHeThong.isVisible()) {
            JPaneHeThong.setVisible(false);
        }

        lblGach.setVisible(true);
        lblGif.setVisible(true);
        this.MenuThu();
    }//GEN-LAST:event_btnQuanLiActionPerformed

    private void btnHeThongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHeThongActionPerformed

        JPaneAbout.setVisible(true);
        if (JPaneGioiThieu.isVisible()) {
            JPaneGioiThieu.setVisible(false);
        }
        if (JPaneTaiKhoan.isVisible()) {
            JPaneTaiKhoan.setVisible(false);
        }
        btnHeThong.setSelected(true);
        btnQuanLi.setSelected(false);
        btnThongKe.setSelected(false);
        btnHome.setSelected(false);
        btnPhanTich.setSelected(false);

        JPaneHeThong.setVisible(true);
        if (JPaneQuanLi.isVisible()) {
            JPaneQuanLi.setVisible(false);
        }
        if (JPaneThongKe.isVisible()) {
            JPaneThongKe.setVisible(false);
        }

        if (JPaneHome.isVisible()) {
            JPaneHome.setVisible(false);
        }
        if (JPanePhanTich.isVisible()) {
            JPanePhanTich.setVisible(false);
        }
//
//        if (lblGach.isVisible() && lblGif.isVisible()) {
//            lblGach.setVisible(false);
//            lblGif.setVisible(false);
//        }

        lblGach.setVisible(true);
        lblGif.setVisible(true);
        this.MenuThu();
    }//GEN-LAST:event_btnHeThongActionPerformed

    private void btnNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNguoiHocActionPerformed
        lblQLnguoiHoc.setVisible(true);
        if (lblQLhocVien.isVisible()) {
            lblQLhocVien.setVisible(false);
        }
        if (lblQLkhoaHoc.isVisible()) {
            lblQLkhoaHoc.setVisible(false);
        }
        if (lblQLchuyenDe.isVisible()) {
            lblQLchuyenDe.setVisible(false);
        }
        if (lblQLnhanVien.isVisible()) {
            lblQLnhanVien.setVisible(false);
        }
        jTabbQLKhoaHoc.setVisible(false);
        jTabbQLNguoiHoc.setVisible(true);
        jTabbQLNhanVien.setVisible(false);
        jTabbQLHocVien.setVisible(false);
        jTabbQLChuyenDe.setVisible(false);
    }//GEN-LAST:event_btnNguoiHocActionPerformed

    private void btnChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenDeActionPerformed
        lblQLchuyenDe.setVisible(true);
        if (lblQLhocVien.isVisible()) {
            lblQLhocVien.setVisible(false);
        }
        if (lblQLkhoaHoc.isVisible()) {
            lblQLkhoaHoc.setVisible(false);
        }
        if (lblQLnguoiHoc.isVisible()) {
            lblQLnguoiHoc.setVisible(false);
        }
        if (lblQLnhanVien.isVisible()) {
            lblQLnhanVien.setVisible(false);
        }
        jTabbQLChuyenDe.setVisible(true);
        jTabbQLKhoaHoc.setVisible(false);
        jTabbQLNguoiHoc.setVisible(false);
        jTabbQLNhanVien.setVisible(false);
        jTabbQLHocVien.setVisible(false);
    }//GEN-LAST:event_btnChuyenDeActionPerformed

    private void btnKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoaHocActionPerformed
        lblQLkhoaHoc.setVisible(true);

        if (lblQLhocVien.isVisible()) {
            lblQLhocVien.setVisible(false);
        }
        if (lblQLchuyenDe.isVisible()) {
            lblQLchuyenDe.setVisible(false);
        }
        if (lblQLnguoiHoc.isVisible()) {
            lblQLnguoiHoc.setVisible(false);
        }

        if (lblQLnhanVien.isVisible()) {
            lblQLnhanVien.setVisible(false);
        }

        jTabbQLKhoaHoc.setVisible(true);
        jTabbQLNguoiHoc.setVisible(false);
        jTabbQLNhanVien.setVisible(false);
        jTabbQLHocVien.setVisible(false);
        jTabbQLChuyenDe.setVisible(false);
    }//GEN-LAST:event_btnKhoaHocActionPerformed

    private void btnNH_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNH_themActionPerformed
        if (checkNH()) {
            this.insertNH();
        }
    }//GEN-LAST:event_btnNH_themActionPerformed

    private void btnNH_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNH_suaActionPerformed
        if (checkNH()) {
            this.updateNH();
        }
    }//GEN-LAST:event_btnNH_suaActionPerformed

    private void btnNH_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNH_XoaActionPerformed
        this.deleteNH();
    }//GEN-LAST:event_btnNH_XoaActionPerformed

    private void btnNH_moiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNH_moiActionPerformed
        this.clearNH();
        this.setStatusNH(true);
    }//GEN-LAST:event_btnNH_moiActionPerformed

    private void btnNL_NHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNL_NHActionPerformed
        this.indexNguoiHoc = 0;
        this.editNH();
    }//GEN-LAST:event_btnNL_NHActionPerformed

    private void btnL_NHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL_NHActionPerformed
        this.indexNguoiHoc--;
        this.editNH();
    }//GEN-LAST:event_btnL_NHActionPerformed

    private void btnNR_NHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNR_NHActionPerformed
        this.indexNguoiHoc = tblNguoiHoc.getRowCount() - 1;
        this.editNH();
    }//GEN-LAST:event_btnNR_NHActionPerformed

    private void btnR_NHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_NHActionPerformed
        this.indexNguoiHoc++;
        this.editNH();
    }//GEN-LAST:event_btnR_NHActionPerformed

    private void jTabbQLNguoiHocAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbQLNguoiHocAncestorAdded
        this.loadNH();
        this.setStatusNH(true);
    }//GEN-LAST:event_jTabbQLNguoiHocAncestorAdded

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        lblQLnhanVien.setVisible(true);

        if (lblQLhocVien.isVisible()) {
            lblQLhocVien.setVisible(false);
        }
        if (lblQLkhoaHoc.isVisible()) {
            lblQLkhoaHoc.setVisible(false);
        }
        if (lblQLnguoiHoc.isVisible()) {
            lblQLnguoiHoc.setVisible(false);
        }
        if (lblQLchuyenDe.isVisible()) {
            lblQLchuyenDe.setVisible(false);
        }
        jTabbQLKhoaHoc.setVisible(false);
        jTabbQLNguoiHoc.setVisible(false);
        jTabbQLNhanVien.setVisible(true);
        jTabbQLHocVien.setVisible(false);
        jTabbQLChuyenDe.setVisible(false);
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnCD_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCD_ThemActionPerformed
        if (checkCD()) {
            this.insertCD();
        }
    }//GEN-LAST:event_btnCD_ThemActionPerformed

    private void btnCD_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCD_SuaActionPerformed
        if (checkCD()) {
            this.updateCD();
        }
    }//GEN-LAST:event_btnCD_SuaActionPerformed

    private void btnCD_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCD_XoaActionPerformed
        if (checkDeleteCD()) {
            this.deleteCD();
        }
    }//GEN-LAST:event_btnCD_XoaActionPerformed

    private void btnCD_MoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCD_MoiActionPerformed
        this.clearCD();
        this.setStatusCD(true);
    }//GEN-LAST:event_btnCD_MoiActionPerformed

    private void btnNL_CDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNL_CDActionPerformed
        this.indexCD = 0;
        this.EditCD();
    }//GEN-LAST:event_btnNL_CDActionPerformed

    private void btnL_CDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL_CDActionPerformed
        this.indexCD--;
        this.EditCD();
    }//GEN-LAST:event_btnL_CDActionPerformed

    private void btnNR_CDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNR_CDActionPerformed
        this.indexCD = tblChuyenDe.getRowCount() - 1;
        this.EditCD();
    }//GEN-LAST:event_btnNR_CDActionPerformed

    private void btnR_CDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_CDActionPerformed
        this.indexCD++;
        this.EditCD();
    }//GEN-LAST:event_btnR_CDActionPerformed

    private void lblAnh_CDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnh_CDMouseClicked
        this.setAnhSize(lblAnh_CD);
    }//GEN-LAST:event_lblAnh_CDMouseClicked

    private void jTabbQLChuyenDeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbQLChuyenDeAncestorAdded
        this.loadCD();
        this.setStatusCD(true);
    }//GEN-LAST:event_jTabbQLChuyenDeAncestorAdded

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 1) {
            this.indexNhanVien = tblNhanVien.rowAtPoint(evt.getPoint());
            if (this.indexNhanVien >= 0) {
                this.EditNV();
                jTabbQLNhanVien.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void jTabbQLNhanVienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbQLNhanVienAncestorAdded
        this.loadNhanVien();
        this.setStatusNV(true);
    }//GEN-LAST:event_jTabbQLNhanVienAncestorAdded

    private void btnR_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_NVActionPerformed
        this.indexNhanVien++;
        this.EditNV();
    }//GEN-LAST:event_btnR_NVActionPerformed

    private void btnNR_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNR_NVActionPerformed
        this.indexNhanVien = tblNhanVien.getRowCount() - 1;
        this.EditNV();
    }//GEN-LAST:event_btnNR_NVActionPerformed

    private void btnL_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL_NVActionPerformed
        this.indexNhanVien--;
        this.EditNV();
    }//GEN-LAST:event_btnL_NVActionPerformed

    private void btnNL_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNL_NVActionPerformed
        this.indexNhanVien = 0;
        this.EditNV();
    }//GEN-LAST:event_btnNL_NVActionPerformed

    private void btnNV_MoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNV_MoiActionPerformed
        this.clearNV();
        this.setStatusNV(true);
    }//GEN-LAST:event_btnNV_MoiActionPerformed

    private void btnNV_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNV_XoaActionPerformed
        this.deleteNV();
    }//GEN-LAST:event_btnNV_XoaActionPerformed

    private void btnNV_SuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNV_SuaActionPerformed
        if (checkNV()) {
            this.updateNV();
        }
    }//GEN-LAST:event_btnNV_SuaActionPerformed

    private void btnNV_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNV_ThemActionPerformed

        if (checkNV()) {
            this.insertNhanVien();
        }


    }//GEN-LAST:event_btnNV_ThemActionPerformed

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked

        if (evt.getClickCount() == 1) {
            this.indexNguoiHoc = tblNguoiHoc.rowAtPoint(evt.getPoint());
            if (this.indexNguoiHoc >= 0) {
                this.editNH();
                jTabbQLNguoiHoc.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblNguoiHocMouseClicked

    private void tblChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyenDeMouseClicked

        if (evt.getClickCount() == 1) {
            this.indexCD = tblChuyenDe.rowAtPoint(evt.getPoint());
            if (this.indexCD >= 0) {
                this.EditCD();
                jTabbQLChuyenDe.setSelectedIndex(0);
            }
        }


    }//GEN-LAST:event_tblChuyenDeMouseClicked

    private void jTabbedPane1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbedPane1AncestorAdded
        fillComboBoxKhoaHoc_TK();
        fillTableBangDiem_TK();
        fillTableNguoiHoc_TK();
        fillTableKhoaHoc_TK();
        fillComboBoxNam_TK();
        fillTableDoanhThu_TK();

    }//GEN-LAST:event_jTabbedPane1AncestorAdded

    private void lblDA_inExMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDA_inExMouseClicked
// this.printIntoExcel();
    }//GEN-LAST:event_lblDA_inExMouseClicked

    private void jTabbQLKhoaHocAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbQLKhoaHocAncestorAdded
        this.fillComboBox();
        this.loadKH();
        this.clearKH();
        this.setStatusKH(true);
    }//GEN-LAST:event_jTabbQLKhoaHocAncestorAdded

    private void jTabbQLHocVienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTabbQLHocVienAncestorAdded


    }//GEN-LAST:event_jTabbQLHocVienAncestorAdded

    private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMouseClicked
        if (evt.getClickCount() == 1) {
            this.indexKhoaHoc = tblKhoaHoc.rowAtPoint(evt.getPoint());
            if (this.indexKhoaHoc >= 0) {
                this.editKH();
                jTabbQLKhoaHoc.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblKhoaHocMouseClicked

    private void btnR_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnR_KHActionPerformed
        this.indexKhoaHoc++;
        this.editKH();
    }//GEN-LAST:event_btnR_KHActionPerformed

    private void btnNR_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNR_KHActionPerformed
        this.indexKhoaHoc = tblKhoaHoc.getRowCount() - 1;
        this.editKH();
    }//GEN-LAST:event_btnNR_KHActionPerformed

    private void btnL_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnL_KHActionPerformed
        this.indexKhoaHoc--;
        this.editKH();
    }//GEN-LAST:event_btnL_KHActionPerformed

    private void btnNL_KHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNL_KHActionPerformed
        this.indexKhoaHoc = 0;
        this.editKH();
    }//GEN-LAST:event_btnNL_KHActionPerformed

    private void btnKH_moiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKH_moiActionPerformed
        this.clearKH();
        this.setStatusKH(true);
    }//GEN-LAST:event_btnKH_moiActionPerformed

    private void btnKH_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKH_xoaActionPerformed
        this.deleteKH();
    }//GEN-LAST:event_btnKH_xoaActionPerformed

    private void btnKH_suaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKH_suaActionPerformed
        if (checkKH()) {
            this.updateKH();
        }
    }//GEN-LAST:event_btnKH_suaActionPerformed

    private void btnKH_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKH_ThemActionPerformed

        if (checkKH()) {
            this.insertKH();
        }

    }//GEN-LAST:event_btnKH_ThemActionPerformed

    private void btnHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHocVienMouseClicked
        lblQLhocVien.setVisible(true);
        if (lblQLkhoaHoc.isVisible()) {
            lblQLkhoaHoc.setVisible(false);
        }
        if (lblQLchuyenDe.isVisible()) {
            lblQLchuyenDe.setVisible(false);
        }
        if (lblQLnguoiHoc.isVisible()) {
            lblQLnguoiHoc.setVisible(false);
        }
        if (lblQLnhanVien.isVisible()) {
            lblQLnhanVien.setVisible(false);
        }
        jTabbQLHocVien.setVisible(true);
        jTabbQLKhoaHoc.setVisible(false);
        jTabbQLNguoiHoc.setVisible(false);
        jTabbQLNhanVien.setVisible(false);
        jTabbQLChuyenDe.setVisible(false);
    }//GEN-LAST:event_btnHocVienMouseClicked

    private void cbbChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChuyenDeActionPerformed
        this.selectComboBoxKH();
    }//GEN-LAST:event_cbbChuyenDeActionPerformed

    private void btnHV_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHV_themActionPerformed
        this.insertHV();
        txtHV_diem.setText("");
    }//GEN-LAST:event_btnHV_themActionPerformed

    private void rdoTatCaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTatCaMouseClicked
        this.fillGridView();
    }//GEN-LAST:event_rdoTatCaMouseClicked

    private void rdoDaNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDaNhapMouseClicked
        this.fillGridView();
    }//GEN-LAST:event_rdoDaNhapMouseClicked

    private void rdoChuaNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChuaNhapMouseClicked
        this.fillGridView();
    }//GEN-LAST:event_rdoChuaNhapMouseClicked

    private void btnHV_CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHV_CapNhatActionPerformed
        this.updateHV();
    }//GEN-LAST:event_btnHV_CapNhatActionPerformed

    private void jPanel18AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel18AncestorAdded
        this.fillComboBox_HocVien();
        this.fillGridView();

    }//GEN-LAST:event_jPanel18AncestorAdded

    private void tblHocVienAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblHocVienAncestorAdded
    }//GEN-LAST:event_tblHocVienAncestorAdded

    private void cbbTK_KhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTK_KhoaHocActionPerformed
        fillTableBangDiem_TK();
    }//GEN-LAST:event_cbbTK_KhoaHocActionPerformed

    private void cbbTK_NamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTK_NamActionPerformed
        fillTableDoanhThu_TK();
    }//GEN-LAST:event_cbbTK_NamActionPerformed

    private void btnNH_TimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNH_TimKiemMouseClicked
        this.loadNH();
    }//GEN-LAST:event_btnNH_TimKiemMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPaneAbout;
    private javax.swing.JPanel JPaneGioiThieu;
    private javax.swing.JPanel JPaneHeThong;
    private javax.swing.JPanel JPaneHome;
    private javax.swing.JPanel JPaneLogout;
    private javax.swing.JPanel JPaneLoogin;
    private javax.swing.JPanel JPaneMenuLogin;
    private javax.swing.JPanel JPaneMenuLogin2;
    private javax.swing.JPanel JPanePhanTich;
    private javax.swing.JPanel JPaneQuanLi;
    private javax.swing.JPanel JPaneTaiKhoan;
    private javax.swing.JPanel JPaneThoat;
    private javax.swing.JPanel JPaneThongKe;
    private javax.swing.JPanel JPaneTime;
    private javax.swing.JLabel backg;
    private javax.swing.JLabel backg1;
    private javax.swing.JLabel background;
    private javax.swing.JLabel backk;
    private javax.swing.JLabel bot;
    private javax.swing.JLabel bot1;
    private javax.swing.JButton btnCD_Moi;
    private javax.swing.JButton btnCD_Sua;
    private javax.swing.JButton btnCD_Them;
    private javax.swing.JButton btnCD_Xoa;
    private javax.swing.JButton btnCan;
    private javax.swing.JButton btnCan1;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChuyenDe;
    private javax.swing.JButton btnEx;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHV_CapNhat;
    private javax.swing.JButton btnHV_them;
    private javax.swing.JButton btnHeThong;
    private javax.swing.JLabel btnHocVien;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnKH_Them;
    private javax.swing.JButton btnKH_moi;
    private javax.swing.JButton btnKH_sua;
    private javax.swing.JButton btnKH_xoa;
    private javax.swing.JButton btnKhoaHoc;
    private javax.swing.JButton btnL_CD;
    private javax.swing.JButton btnL_KH;
    private javax.swing.JButton btnL_NH;
    private javax.swing.JButton btnL_NV;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLoginOK;
    private javax.swing.JButton btnLoginOK1;
    private javax.swing.JLabel btnNH_TimKiem;
    private javax.swing.JButton btnNH_Xoa;
    private javax.swing.JButton btnNH_moi;
    private javax.swing.JButton btnNH_sua;
    private javax.swing.JButton btnNH_them;
    private javax.swing.JButton btnNL_CD;
    private javax.swing.JButton btnNL_KH;
    private javax.swing.JButton btnNL_NH;
    private javax.swing.JButton btnNL_NV;
    private javax.swing.JButton btnNR_CD;
    private javax.swing.JButton btnNR_KH;
    private javax.swing.JButton btnNR_NH;
    private javax.swing.JButton btnNR_NV;
    private javax.swing.JButton btnNV_Moi;
    private javax.swing.JButton btnNV_Sua;
    private javax.swing.JButton btnNV_Them;
    private javax.swing.JButton btnNV_Xoa;
    private javax.swing.JButton btnNguoiHoc;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPhanTich;
    private javax.swing.JButton btnQuanLi;
    private javax.swing.JButton btnR_CD;
    private javax.swing.JButton btnR_KH;
    private javax.swing.JButton btnR_NH;
    private javax.swing.JButton btnR_NV;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnYes;
    private javax.swing.JButton btnYes1;
    private javax.swing.JComboBox<String> cbbChuyenDe;
    private javax.swing.JComboBox<String> cbbGioiTinh_NH;
    private javax.swing.JComboBox<String> cbbHocVien;
    private javax.swing.JComboBox<String> cbbTK_KhoaHoc;
    private javax.swing.JComboBox<String> cbbTK_Nam;
    private javax.swing.JCheckBox chkGhiNho;
    private javax.swing.JCheckBox chkGhiNho1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPaneExit;
    private javax.swing.JPanel jPaneForm;
    private javax.swing.JPanel jPaneMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelQLduAn;
    private javax.swing.JPanel jPanelQLnguoiDung;
    private javax.swing.JPanel jPanelQLnguoiDung1;
    private javax.swing.JPanel jPanelQLnguoiDung2;
    private javax.swing.JPanel jPanelQLsanPham;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbQLChuyenDe;
    private javax.swing.JTabbedPane jTabbQLHocVien;
    private javax.swing.JTabbedPane jTabbQLKhoaHoc;
    private javax.swing.JTabbedPane jTabbQLNguoiHoc;
    private javax.swing.JTabbedPane jTabbQLNhanVien;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAnh_CD;
    private javax.swing.JLabel lblBackG;
    private javax.swing.JLabel lblDA_inEx;
    private javax.swing.JLabel lblDA_inEx1;
    private javax.swing.JLabel lblEye;
    private javax.swing.JLabel lblEyyy;
    private javax.swing.JLabel lblGach;
    private javax.swing.JLabel lblGif;
    private javax.swing.JLabel lblGioiThieu;
    private javax.swing.JLabel lblHide1;
    private javax.swing.JLabel lblHide2;
    private javax.swing.JLabel lblHide3;
    private javax.swing.JLabel lblHideLogout;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblLogin1;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblNameTK;
    private javax.swing.JLabel lblNewTK;
    private javax.swing.JLabel lblQLchuyenDe;
    private javax.swing.JLabel lblQLhocVien;
    private javax.swing.JLabel lblQLkhoaHoc;
    private javax.swing.JLabel lblQLnguoiHoc;
    private javax.swing.JLabel lblQLnhanVien;
    private javax.swing.JLabel lblShow1;
    private javax.swing.JLabel lblShow2;
    private javax.swing.JLabel lblShow3;
    private javax.swing.JLabel lblShowLogout;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JLabel lblThemTK;
    private javax.swing.JLabel lblThoat;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lbl_BackG_Home;
    private javax.swing.JLabel left;
    private javax.swing.JLabel left1;
    private javax.swing.JLabel logo;
    private javax.swing.JRadioButton rdoChuaNhap;
    private javax.swing.JRadioButton rdoDaNhap;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JLabel right;
    private javax.swing.JLabel right1;
    private javax.swing.JLabel slide;
    private javax.swing.JTable tblChuyenDe;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblTK_BangDiem;
    private javax.swing.JTable tblTK_DoanhThu;
    private javax.swing.JTable tblTK_NguoiHoc;
    private javax.swing.JTable tblTK_TongHop;
    private javax.swing.JLabel top;
    private javax.swing.JLabel top1;
    private javax.swing.JTextField txtCD_ID;
    private javax.swing.JTextField txtCD_Ten_CD;
    private javax.swing.JTextField txtCD_ThoiLuong;
    private javax.swing.JTextField txtCD_hocPhi;
    private javax.swing.JTextArea txtCD_mota;
    private javax.swing.JTextField txtHV_diem;
    private javax.swing.JTextField txtKH_HocPhi;
    private javax.swing.JTextField txtKH_MaNV;
    private javax.swing.JTextArea txtKH_ghiChu;
    private javax.swing.JTextField txtKH_ngayKhaiGiang;
    private javax.swing.JTextField txtKH_ngayTao;
    private javax.swing.JTextField txtKH_thoiLuong;
    private javax.swing.JTextField txtNH_DienThoai;
    private javax.swing.JTextField txtNH_Email;
    private javax.swing.JTextArea txtNH_GhiChu;
    private javax.swing.JTextField txtNH_HoTen;
    private javax.swing.JTextField txtNH_ID;
    private javax.swing.JTextField txtNH_NgaySinh;
    private javax.swing.JTextField txtNH_TimKiem;
    private javax.swing.JTextField txtNV_ID;
    private javax.swing.JTextField txtNV_xnMatKhau;
    private javax.swing.JTextField txtNVhoTen;
    private javax.swing.JTextField txtNVmatKhau;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPass1;
    private javax.swing.JPasswordField txtPassCu;
    private javax.swing.JPasswordField txtPassMoi;
    private javax.swing.JPasswordField txtPassXN;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserName1;
    // End of variables declaration//GEN-END:variables
}
