package ais.app;

import ais.controller.InputController;
import ais.controller.RightPaneController;
import ais.model.AISAlarmModel;
import ais.model.CommunicationTestModel;
import ais.model.DestinationLoadModel;
import ais.model.DiagnosisLogModel;
import ais.model.DraughtInputModel;
import ais.model.EditAndTxModel;
import ais.model.ETAInputModel;
import ais.model.InterrogationModel;
import ais.model.NavStatus;
import ais.model.OwnShipInfo;
import ais.model.PersonsInputModel;
import ais.model.RxMessage;
import ais.model.RxTrayModel;
import ais.model.SelfDiagnosisModel;
import ais.model.settingItem;
import ais.model.ShipInfo;
import ais.model.ShipManager;
import ais.model.ShipTypeCargoInputModel;
import ais.model.ShipTypeUSInputModel;
import ais.model.TextEditModel;
import ais.model.TxMessage;
import ais.model.TxTrayModel;
import ais.model.VoyageEditSession;
import ais.network.AisUdpBroadcaster;
import ais.network.UdpReceiver;
import ais.util.NavigationUtil;
import ais.util.OwnShipJsonUtil;
import ais.util.Paginator;
import ais.view.AISAlarmCurrentView;
import ais.view.BearingView;
import ais.view.CommunicationAckPopupView;
import ais.view.CommunicationTestView;
import ais.view.CommunicationTxView;
import ais.view.ControllerLogView;
import ais.view.DestinationInputView;
import ais.view.DestinationLoadView;
import ais.view.DISPView;
import ais.view.DraughtInputView;
import ais.view.EditAndTxSubMenuView;
import ais.view.EditAndTxTxConfirmView;
import ais.view.EditAndTxView;
import ais.view.ETAInputView;
import ais.view.GraphicView;
import ais.view.InterrogationSubMenuView;
import ais.view.InterrogationTxResultView;
import ais.view.InterrogationTxView;
import ais.view.InterrogationView;
import ais.view.ListSubView;
import ais.view.MaintenanceMenuView;
import ais.view.MenuView;
import ais.view.MessageMenuView;
import ais.view.NAMEView;
import ais.view.NavStatusView;
import ais.view.OtherShipDetailView;
import ais.view.OtherShipSubMenuView;
import ais.view.OwnShipDetail1View;
import ais.view.OwnShipDetail2View;
import ais.view.OwnShipTRXView;
import ais.view.PersonsInputView;
import ais.view.PosnTimeView;
import ais.view.RoundButton;
import ais.view.RxTrayDetailView;
import ais.view.RxTrayListView;
import ais.view.RxTraySubMenuView;
import ais.view.RxTrayTextView;
import ais.view.SelfDiagnosisView;
import ais.view.ShipListView;
import ais.view.ShipTypeCargoInputView;
import ais.view.ShipTypeUSInputView;
import ais.view.SORTView;
import ais.view.TextEditView;
import ais.view.TextViewScreen;
import ais.view.TransponderLogView;
import ais.view.TXMessageDetailView;
import ais.view.TxResultView;
import ais.view.TxTransmittingView;
import ais.view.TXTrayListView;
import ais.view.TXTraySubMenuView;
import ais.view.VoyageSubView;
import ais.view.VoyageView;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;




public class AISMain2 {
    private static final Logger LOGGER = Logger.getLogger(AISMain2.class.getName());

    /* =====================================================
     * 定数・通信関連
     * ===================================================== */
    private static final int PORT = 17020;
    private UdpReceiver udpReceiver;

    /* =====================================================
     * フレーム・レイアウト基盤
     * ===================================================== */
    private JFrame frame;
    private JPanel cardPanel;
    private JTextArea textArea;

    /* =====================================================
     * メイン View 群（CardLayout 管理対象）
     * ===================================================== */
    private ShipListView shipListView;
    private MenuView menuView;
    private MessageMenuView messageMenuView;
    private EditAndTxModel editAndTxModel;
    private EditAndTxView editAndTxView;
    private EditAndTxSubMenuView editAndTxSubMenuView;
    private TextEditModel textEditModel;
    private TextEditView textEditView;
    private EditAndTxTxConfirmView txConfirmView;
    private TxTransmittingView txTransmittingView;
    private TxResultView txResultView;
    private TxTrayModel txTrayModel;
    private TXTrayListView txTrayListView;
    private TXTraySubMenuView txTraySubMenuView;
    private TextViewScreen txTextView;
    private TXMessageDetailView txMessageDetailView;
    private RxTrayModel rxTrayModel;
    private RxTrayListView rxTrayListView;
    private RxTraySubMenuView rxTraySubMenuView;
    private RxTrayTextView rxTrayTextView;
    private RxTrayDetailView rxTrayDetailView;
    private InterrogationModel interrogationModel;
    private InterrogationView interrogationView;
    private InterrogationSubMenuView interrogationSubMenuView;
    private InterrogationTxView interrogationTxView;
    private InterrogationTxResultView interrogationTxResultView;
    private MaintenanceMenuView maintenanceMenuView;
    private SelfDiagnosisModel selfDiagnosisModel;
    private SelfDiagnosisView selfDiagnosisView;
    private TransponderLogView transponderLogView;
    private ControllerLogView controllerLogView;
    private ControllerLogView controllerLanLogView;
    private DiagnosisLogModel diagnosisLogModel;
    private CommunicationAckPopupView communicationAckPopupView;
    private CommunicationTestView communicationTestView;
    private CommunicationTestModel communicationTestModel;
    private CommunicationTxView communicationTxView;
    private AISAlarmCurrentView aisAlarmCurrentView;
    private AISAlarmModel aisAlarmModel;
    
    private VoyageView voyageView;
    private VoyageSubView voyageSubView;
    private NavStatusView navStatusView;
    private DestinationInputView destinationInputView;
    private ETAInputView etaInputView;
    private ETAInputModel etaInputModel;
    private DraughtInputView draughtInputView;
    private DraughtInputModel draughtInputModel;
    private PersonsInputModel personsInputModel;
    private PersonsInputView personsInputView;
    private ShipTypeUSInputModel shipTypeUSInputModel;  // VOYAGE DATA 入力項目用（Ship Type U.S.）
    private ShipTypeUSInputView shipTypeUSInputView;
    private ShipTypeCargoInputModel shipTypeCargoModel; // 船種、積載物、状態の設定
    private ShipTypeCargoInputView shipTypeCargoView;
    private DestinationLoadView destinationLoadView;    // DEST LOAD
    private DestinationLoadModel destinationLoadModel;


    private ListSubView listSubView;
    private OtherShipDetailView otherShipDetailView;
    private OtherShipSubMenuView otherShipSubMenuView;

    private GraphicView graphicView;
    private PosnTimeView PosnTimeView;

    private BearingView bearingView;
    private SORTView sortView;
    private NAMEView nameView;
    private DISPView dispView;

    private OwnShipDetail1View ownShipDetail1View;
    private OwnShipDetail2View ownShipDetail2View;
    private OwnShipTRXView ownShipTRXView;

    /* =====================================================
     * 入力制御（Controller）
     * ===================================================== */
    private InputController inputController;
    private static RoundButton lastButton = null;
    private VoyageEditSession voyageSession;

    /* =====================================================
     * 右ペイン（説明表示）
     * ===================================================== */
    private JEditorPane infoPane;
    private JScrollPane infoScroll;
    private RightPaneController rightPaneController;

    /* =====================================================
     * データ管理
     * ===================================================== */
    private final ShipManager shipManager;
    private OwnShipInfo myship;

    /* =====================================================
     * 画面状態管理
     * ===================================================== */
    private ScreenId currentScreen;

    /* =====================================================
     * 他船一覧・ページング関連
     * ===================================================== */
    private int pageIndex = 1;
    private Paginator otherShipPager = new Paginator(1, 9);
    private ShipInfo currentShip;

    /* =====================================================
     * 自船ページング関連
     * ===================================================== */
    private Paginator ownShipPager1 = new Paginator(1, 6);
    private Paginator ownShipPager2 = new Paginator(1, 4);
    private Paginator trxPager = new Paginator(1, 4);

    /* =====================================================
     * 
     * ===================================================== */
    

    public AISMain2() {
        shipManager = ShipManager.getInstance();
        myship = shipManager.getOwnShip();
        initGui();  // ← GUI構築・表示
        initViewActions();

        udpReceiver = new UdpReceiver(
                PORT,
                shipManager,
                () -> SwingUtilities.invokeLater(this::refreshTable),
                msg -> SwingUtilities.invokeLater(() -> textArea.append(msg))
        );
        udpReceiver.start();
    }

    @SuppressWarnings("Convert2Lambda")
    private void initGui() {

        // =====================================================
        // フレーム・基盤パネル初期化
        // =====================================================
        // アプリケーション全体のウィンドウ設定と
        // CardLayout を持つメインパネルを初期化する

        this.frame = new JFrame("AISコントローラ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 起動時に最大化
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // ===== 左パネル =====
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(0xD9, 0xD9, 0xD9)); // グレー背景
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.insets = new Insets(20, 30, 20, 30);
        leftGbc.gridx = 0;
        leftGbc.fill = GridBagConstraints.HORIZONTAL;
        leftGbc.weightx = 1.0;

        // ---- JRC ロゴ ----
        JLabel jrcLabel = new JLabel("JRC");
        jrcLabel.setFont(new Font("SansSerif", Font.BOLD, 42));
        jrcLabel.setHorizontalAlignment(SwingConstants.LEFT);
        leftGbc.gridy = 0;
        leftGbc.anchor = GridBagConstraints.NORTHWEST;
        leftPanel.add(jrcLabel, leftGbc);

        // ---- テキストエリア ----
        this.textArea = new JTextArea();
        textArea.setBackground(new Color(200, 255, 200));
        textArea.setFont(new Font("Meiryo", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        // === CardLayoutで切り替えるためのパネル ===
        this.cardPanel = new JPanel(new CardLayout());
        cardPanel.setPreferredSize(new Dimension(400, 200));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        // ★従来のテキストエリア（Card1 として登録）
        cardPanel.add(scrollPane, ScreenId.TEXT.cardName());

        myship = ShipManager.getInstance().getOwnShip();
        

        // =====================================================
        // 各画面 View インスタンス生成
        // =====================================================
        // CardLayout に登録する各画面の View を生成する
        // （この時点では表示・遷移は行わない）
        shipListView = new ShipListView();                     // 他船一覧表示 
        menuView = new MenuView();                             // MainMENU 表示
        messageMenuView = new MessageMenuView();               // MESSAGE MENU
        editAndTxModel = new EditAndTxModel();
        editAndTxView = new EditAndTxView(editAndTxModel);
        editAndTxSubMenuView = new EditAndTxSubMenuView();
        textEditView = new TextEditView(textEditModel);
        txConfirmView = new EditAndTxTxConfirmView();
        txTransmittingView = new TxTransmittingView();
        txResultView = new TxResultView();
        txTrayModel = new TxTrayModel();
        txTrayListView = new TXTrayListView();
        txTraySubMenuView = new TXTraySubMenuView();
        txTextView = new TextViewScreen();
        txMessageDetailView = new TXMessageDetailView();
        rxTrayModel = new RxTrayModel();
        rxTrayListView = new RxTrayListView();
        rxTraySubMenuView = new RxTraySubMenuView();
        rxTrayTextView = new RxTrayTextView();
        rxTrayDetailView = new RxTrayDetailView();
        interrogationModel = new InterrogationModel();
        interrogationView = new InterrogationView(interrogationModel);
        interrogationSubMenuView = new InterrogationSubMenuView();
        interrogationTxView = new InterrogationTxView();
        interrogationTxResultView = new InterrogationTxResultView();
        maintenanceMenuView = new MaintenanceMenuView();
        selfDiagnosisModel = new SelfDiagnosisModel();
        selfDiagnosisView = new SelfDiagnosisView();
        transponderLogView = new TransponderLogView();
        controllerLogView = new ControllerLogView("CONTROLLER");
        controllerLanLogView = new ControllerLogView("CONTROLLER LAN");
        diagnosisLogModel = new DiagnosisLogModel();
        communicationAckPopupView = new CommunicationAckPopupView();
        communicationTestView = new CommunicationTestView();
        communicationTestModel = new CommunicationTestModel();
        communicationTxView = new CommunicationTxView();
        aisAlarmCurrentView = new AISAlarmCurrentView();

        aisAlarmModel = new AISAlarmModel();

        voyageView = new VoyageView(myship);                   // VOYAGE DATA 用 JList
        voyageSubView = new VoyageSubView();                   // VOYAGE DATA SUB MENU JList
        navStatusView = new NavStatusView();                   // 航行状態設定用 JList
        otherShipDetailView = new OtherShipDetailView();       // 他船詳細表示用
        otherShipSubMenuView = new OtherShipSubMenuView();     // 他船詳細表示用 SUB MENU JList
        graphicView = new GraphicView();                       // グラフィック画面用
        ownShipDetail1View = new OwnShipDetail1View();         // 自船詳細表示１画面用
        ownShipDetail2View = new OwnShipDetail2View();         // 自船詳細表示２画面用
        ownShipTRXView = new OwnShipTRXView();                 // 自船無線運用画面用
        PosnTimeView = new PosnTimeView();                     // 位置/日時画面用
        listSubView = new ListSubView();                       // 他船一覧表示用 SUB MENU JList
        bearingView = new BearingView();                       // 他船一覧表示用 SUB MENU BEARING設定画面用
        sortView = new SORTView();                             //   他船一覧表示用 SUB MENU SORT設定画面用
        nameView = new NAMEView();                             // 他船一覧表示用 SUB MENU NAME設定画面用
        dispView = new DISPView();                             // 他船一覧表示用 SUB MENU DISP設定画面用
        destinationInputView = new DestinationInputView();     // 目的地入力文字パッド
        etaInputModel = new ETAInputModel();                   // ETAの設定画面
        etaInputView = new ETAInputView(etaInputModel);
        draughtInputModel = new DraughtInputModel();           // 喫水の設定
        draughtInputView = new DraughtInputView(draughtInputModel);
        personsInputModel = new PersonsInputModel();           // 搭乗人員設定
        personsInputView = new PersonsInputView(personsInputModel);
        shipTypeUSInputModel = new ShipTypeUSInputModel();     // Ship Type U.S. 搭乗人員数設定
        shipTypeUSInputView = new ShipTypeUSInputView(shipTypeUSInputModel);
        shipTypeCargoModel = new ShipTypeCargoInputModel();    // 船種、積載物、状態の設定
        shipTypeCargoView = new ShipTypeCargoInputView(shipTypeCargoModel);
        destinationLoadView = new DestinationLoadView();       // DEST LOAD
        destinationLoadModel = new DestinationLoadModel();

        // =====================================================
        // CardLayout への画面登録
        // =====================================================
        //画面名（card 名）と View を対応付けて登録する
        // 遷移判断は Controller 側で行う
        cardPanel.add(shipListView, ScreenId.LIST.cardName());
        cardPanel.add(menuView, ScreenId.MENU.cardName());
        cardPanel.add(messageMenuView, ScreenId.MESSAGE.cardName());
        cardPanel.add(editAndTxView, ScreenId.EDIT_AND_TX.cardName());
        cardPanel.add(editAndTxSubMenuView, ScreenId.EDIT_AND_TX_SUB.cardName());
        cardPanel.add(textEditView, ScreenId.TEXT_EDIT.cardName());
        cardPanel.add(txConfirmView, ScreenId.EDIT_AND_TX_TX_CONFIRM.cardName());
        cardPanel.add(txTransmittingView, ScreenId.EDIT_AND_TX_TX_TRANSMITTING.cardName());
        cardPanel.add(txResultView, ScreenId.EDIT_AND_TX_TX_RESULT.cardName());
        cardPanel.add(txTrayListView, ScreenId.TX_TRAY.cardName());
        cardPanel.add(txTextView, ScreenId.TX_TRAY_TEXT.cardName());
        cardPanel.add(txTraySubMenuView, ScreenId.TX_TRAY_SUB.cardName());
        cardPanel.add(txMessageDetailView, ScreenId.TX_TRAY_DETAIL.cardName());
        cardPanel.add(rxTrayListView, ScreenId.RX_TRAY.cardName());
        cardPanel.add(rxTraySubMenuView, ScreenId.RX_TRAY_SUB.cardName());
        cardPanel.add(rxTrayTextView, ScreenId.RX_TRAY_TEXT.cardName());
        cardPanel.add(rxTrayDetailView, ScreenId.RX_TRAY_DETAIL.cardName());
        cardPanel.add(interrogationView, ScreenId.INTERROGATION.cardName());
        cardPanel.add(interrogationSubMenuView, ScreenId.INTERROGATION_SUB.cardName());
        cardPanel.add(interrogationTxView, ScreenId.INTERROGATION_TX.cardName());
        cardPanel.add(interrogationTxResultView, ScreenId.INTERROGATION_TX_RESULT.cardName());
        cardPanel.add(maintenanceMenuView, ScreenId.MAINTENANCE_MENU.cardName());
        cardPanel.add(selfDiagnosisView, ScreenId.SELF_DIAGNOSIS.cardName());
        cardPanel.add(transponderLogView, ScreenId.TRANSPONDER_LOG.cardName());
        cardPanel.add(controllerLogView, ScreenId.CONTROLLER_LOG.cardName());
        cardPanel.add(controllerLanLogView, ScreenId.CONTROLLER_LAN_LOG.cardName());
        cardPanel.add(communicationAckPopupView, ScreenId.COMMUNICATION_ACK_POPUP.cardName());
        cardPanel.add(communicationTestView, ScreenId.COMMUNICATION_TEST.cardName());
        cardPanel.add(communicationTxView, ScreenId.COMMUNICATION_TEST_TX.cardName());
        cardPanel.add(aisAlarmCurrentView, ScreenId.AIS_ALARM.cardName());
        

        cardPanel.add(voyageView, ScreenId.VOYAGE.cardName());
        cardPanel.add(voyageSubView, ScreenId.VOYAGE_SUB.cardName());
        cardPanel.add(navStatusView, ScreenId.NAV_STATUS.cardName());
        cardPanel.add(otherShipDetailView, ScreenId.OTHER_DETAIL.cardName());
        cardPanel.add(otherShipSubMenuView, ScreenId.OTHER_DETAIL_SUB.cardName());
        cardPanel.add(graphicView, ScreenId.GRAPHIC.cardName());
        cardPanel.add(ownShipDetail1View, ScreenId.OWN_DETAIL1.cardName());
        cardPanel.add(ownShipDetail2View, ScreenId.OWN_DETAIL2.cardName());
        cardPanel.add(ownShipTRXView, ScreenId.OWN_TRX.cardName());
        cardPanel.add(PosnTimeView, ScreenId.POSN_TIME.cardName());
        cardPanel.add(listSubView, ScreenId.LIST_SUB.cardName());
        cardPanel.add(bearingView, ScreenId.BEARING.cardName());
        cardPanel.add(sortView, ScreenId.SORT.cardName());
        cardPanel.add(nameView, ScreenId.NAME.cardName());
        cardPanel.add(dispView, ScreenId.DISP.cardName());
        cardPanel.add(destinationInputView, ScreenId.DESTINATION.cardName());
        cardPanel.add(etaInputView, ScreenId.ETA.cardName());
        cardPanel.add(draughtInputView, ScreenId.DRAUGHT.cardName());
        cardPanel.add(personsInputView, ScreenId.PERSONS.cardName());
        cardPanel.add(shipTypeUSInputView, ScreenId.SHIP_TYPE_US.cardName());
        cardPanel.add(shipTypeCargoView, ScreenId.SHIP_TYPE_CARGO.cardName());
        cardPanel.add(destinationLoadView, ScreenId.DESTINATION_LOAD.cardName());

        

        // 初期表示はリストにする
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        showCard(ScreenId.LIST);

        // === レイアウトへ反映 ===
        leftGbc.gridy = 1;
        leftGbc.weighty = 0.6;
        leftGbc.fill = GridBagConstraints.BOTH;
        leftPanel.add(cardPanel, leftGbc);

        // =====================================================
        // 右ペイン（操作説明・ヘルプ表示）構築
        // =====================================================
        // ボタンフォーカスに応じて説明文を表示するための
        // JEditorPane + ScrollPane を初期化
        this.infoPane = new JEditorPane();
        this.rightPaneController = new RightPaneController(infoPane);
        infoPane.setContentType("text/html");
        infoPane.setEditable(false);
        infoPane.setText("<html><body style='padding:12px;color:#404040;font-family:Sans-Serif;'></body></html>");
        infoPane.setBackground(new Color(0xF9F9F9));
        infoPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String desc = e.getDescription();
                if (desc != null) {
                    if (desc.startsWith("#")) {
                        desc = desc.substring(1); 
                    }else if (desc.contains("#")) {
                        desc = desc.substring(desc.indexOf('#') + 1);
                    }
                    infoPane.scrollToReference(desc);
                }
            }
        });

        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setBorder(BorderFactory.createTitledBorder("詳細説明"));
        infoScroll.setPreferredSize(new Dimension(300, 300));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(infoScroll, BorderLayout.CENTER);

        // === 各ボタンの説明文 ===
        final Map<String, String> descriptions = new HashMap<>();
        descriptions.put("USER", "USER: ユーザーキー — よく使う画面をワンキーで呼び出せます。");
        descriptions.put("SUB", "SUB: サブメニュー表示 — 現在の画面に応じた設定や補助操作を開きます。");
        descriptions.put("MENU", "MENU: メインメニュー表示 — 各種サブメニューへ移動します。");
        descriptions.put("‥", "（省略）: 任意機能の割当位置です。");
        descriptions.put("PWR", "PWR: 電源/コントラスト — 電源操作やコントラスト調整を行います。");
        descriptions.put("DIM", "DIM: バックライト（輝度）調整を行います。");
        descriptions.put("DISP", "DISP: 表示モード切替 — リスト/位置/グラフィック/自船情報などを切替。");
        descriptions.put("CLR", "CLR: クリア／戻る — メニュー戻りや入力取り消し、アラーム一時停止等。");
        descriptions.put("ENTER", "ENTER: 決定／確定 — メニューや入力の確定に使用します。");
        descriptions.put("↑", "↑: カーソル上移動");
        descriptions.put("↓", "↓: カーソル下移動");
        descriptions.put("←", "←: 左移動/スクロール");
        descriptions.put("→", "→: 右移動/スクロール");

        // =====================================================
        // 左パネル（操作ボタン群）構築
        // =====================================================
        // SUB / MENU / DISP / CLR / 矢印 / ENTER ボタンを生成し
        // 視覚的レイアウトを構成する
        JPanel bottomLeft = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBL = new GridBagConstraints();
        gbcBL.fill = GridBagConstraints.BOTH;
        gbcBL.insets = new Insets(6, 6, 6, 6);

        JPanel leftButtons = new JPanel(new GridLayout(2, 4, 8, 8));
        String[] labels = {"USER", "SUB", "MENU", "‥", "PWR", "DIM", "DISP", "CLR"};
        for (String label : labels) {
            if ("‥".equals(label)) {
                JLabel dummy = new JLabel("・・", SwingConstants.CENTER);
                dummy.setForeground(Color.DARK_GRAY);
                leftButtons.add(dummy);
                continue;
            }
            RoundButton rb = createButtonWithRightPane(label, descriptions.get(label), rightPaneController);

            // =====================================================
            // InputController 初期化・接続
            // =====================================================
            // 入力イベントの解釈をすべて Controller に委譲する
            // AISMain2 は UI 操作 API のみを提供する
            this.inputController = new InputController(this);

            // SUBボタンクリック時のマウスイベント
            if ("SUB".equals(label)) {
                rb.addActionListener(e -> inputController.onSubPressed());
            }

            // MENUボタンクリック時のマウスイベント
            if ("MENU".equals(label)) {
                rb.addActionListener(e -> inputController.onMenuPressed());
            }

            //　PWRボタンクリック時のマウスイベント
            if ("PWR".equals(label)) {
                rb.addActionListener(e -> System.exit(0));
            }

            // DISPボタンクリック時のマウスイベント
            if ("DISP".equals(label)) {
                rb.addActionListener(e -> inputController.onDispPressed());
            }

            //　CLRボタンクリック時のマウスイベント
            if ("CLR".equals(label)) {
                rb.addActionListener(e -> inputController.onClrPressed());
            }

            leftButtons.add(rb);
        }

        // 矢印パッド
        JPanel arrowPad = new JPanel(new GridLayout(3, 3, 4, 4));

        // ↑ ボタン
        RoundButton upButton = createButtonWithRightPane("↑", descriptions.get("↑"), rightPaneController);
        upButton.addActionListener(e -> inputController.onUpPressed());

        arrowPad.add(new JLabel());
        arrowPad.add(upButton);
        upButton.setFocusable(false);
        arrowPad.add(new JLabel());

        // ← ボタン
        RoundButton leftButton = createButtonWithRightPane("←", descriptions.get("←"), rightPaneController);
        leftButton.addActionListener(e -> inputController.onLeftPressed());
        arrowPad.add(leftButton);

        // ENTER ボタン
        RoundButton enterButton = createButtonWithRightPane("ENTER", descriptions.get("ENTER"), rightPaneController);
        arrowPad.add(enterButton);

        // → ボタン
        RoundButton rightButton = createButtonWithRightPane("→", descriptions.get("→"), rightPaneController);
        rightButton.addActionListener(e -> inputController.onRightPressed());
        arrowPad.add(rightButton);

        // ↓ ボタン
        RoundButton downButton = createButtonWithRightPane("↓", descriptions.get("↓"), rightPaneController);
        downButton.addActionListener(e -> inputController.onDownPressed());

        arrowPad.add(new JLabel());
        arrowPad.add(downButton);
        downButton.setFocusable(false);
        arrowPad.add(new JLabel());

        // ENTERボタンのマウスクリックイベント
        enterButton.addActionListener(evt -> inputController.onEnterPressed());

        gbcBL.gridx = 0;
        gbcBL.gridy = 0;
        gbcBL.weightx = 0.7;
        bottomLeft.add(leftButtons, gbcBL);

        gbcBL.gridx = 1;
        gbcBL.weightx = 0.3;
        bottomLeft.add(arrowPad, gbcBL);

        leftGbc.gridy = 2;
        leftGbc.weighty = 0.4;
        leftPanel.add(bottomLeft, leftGbc);

        // === 配置 ===
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.weightx = 0.9;
        frame.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        frame.add(rightPanel, gbc);

        frame.setVisible(true);

    }

    // ===== Getter メソッド群 =====
    public String getCurrentCard() {
        return currentScreen == null ? null : currentScreen.cardName();
    }

    public ScreenId getCurrentScreen() {
        return currentScreen;
    }

    public OwnShipInfo getOwnShipInfo() {
        return myship;
    }

    public ShipListView getShipListView() {
        return shipListView;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public MessageMenuView getMessageMenuView() {
        return messageMenuView;
    }

    public EditAndTxSubMenuView getEditAndTxSubMenuView() {
        return editAndTxSubMenuView;
    }

    public EditAndTxModel getEditAndTxModel() {
        return editAndTxModel;
    }

    public EditAndTxView getEditAndTxView() {
        return editAndTxView;
    }

    public TextEditView getTextEditView() {
        return textEditView;
    }

    public EditAndTxTxConfirmView getTxConfirmView() {
        return txConfirmView;
    }

    /* ===== TX TRAY Getter ===== */
    public TXTrayListView getTxTrayListView() {
        return txTrayListView;
    }

    public TXTraySubMenuView getTxTraySubMenuView() {
        return txTraySubMenuView;
    }

    public TxResultView getTxResultView() {
        return txResultView;
    }

    /* ===== RX TRAY Getter ===== */
    public RxTrayListView getRxTrayListView() {
        return rxTrayListView;
    }

    public RxTraySubMenuView getRxTraySubMenuView() {
        return rxTraySubMenuView;
    }

    /* ===== INTERROGATION Getter ===== */
    public InterrogationView getInterrogationView() {
        return interrogationView;
    }

    public InterrogationModel getInterrogationModel() {
        return interrogationModel;
    }

    public InterrogationSubMenuView getInterrogationSubMenuView() {
        return interrogationSubMenuView;
    }

    /* ===== MAINTENACE Getter ===== */
    public MaintenanceMenuView getMaintenanceMenuView(){
        return maintenanceMenuView;
    }

    public SelfDiagnosisModel getSelfDiagnosisModel() {
        return selfDiagnosisModel;
    }

    public SelfDiagnosisView getSelfDiagnosisView() {
        return selfDiagnosisView;
    }

    public ControllerLogView getControllerLogView() {
        return controllerLogView;
    }

    public ControllerLogView getControllerLanLogView() {
        return controllerLanLogView;
    }

    public TransponderLogView getTransponderLogView() {
        return transponderLogView;
    }

    public DiagnosisLogModel getDiagnosisLogModel() {
        return diagnosisLogModel;
    }

    public CommunicationTestModel getCommunicationTestModel() {
        return communicationTestModel;
    }

    public CommunicationTestView getCommunicationTestView() {
        return communicationTestView;
    }

    public CommunicationTxView getCommunicationTxView() {
        return communicationTxView;
    }

    public AISAlarmModel getAISAlarmModel() {
        return aisAlarmModel;
    }

    public AISAlarmCurrentView getAISAlarmCurrentView() {
        return aisAlarmCurrentView;
    }

    public VoyageView getVoyageView() {
        return voyageView;
    }

    public VoyageSubView getVoyageSubView() {
        return voyageSubView;
    }

    public ListSubView getListSubView() {
        return listSubView;
    }

    public OtherShipSubMenuView getOtherShipSubMenuView() {
        return otherShipSubMenuView;
    }

    public NavStatusView getNavStatusView() {
        return navStatusView;
    }

    public DestinationInputView getDestinationInputView() {
        return destinationInputView;
    }

    public DestinationLoadView getDestinationLoadView() {
        return destinationLoadView;
    }

    public ETAInputView getETAInputView(){
        return etaInputView;
    }

    public ETAInputModel getETAInputModel() {
        return etaInputModel;
    }

    public DraughtInputModel getDraughtInputModel() {
        return draughtInputModel;
    }

    public DraughtInputView getDraughtInputView() {
        return draughtInputView;
    }

    public PersonsInputModel getPersonsInputModel() {
        return personsInputModel;
    }

    public PersonsInputView getPersonsInputView() {
        return personsInputView;
    }

    public ShipTypeUSInputModel getShipTypeUSInputModel() {
        return shipTypeUSInputModel;
    }

    public ShipTypeUSInputView getShipTypeUSInputView() {
        return shipTypeUSInputView;
    }

    public ShipTypeCargoInputModel getShipTypeCargoModel() {
        return  shipTypeCargoModel;
    }

    public ShipTypeCargoInputView getShipTypeCargoView() {
        return  shipTypeCargoView;
    }

    public VoyageEditSession getVoyageSession() {
        return voyageSession;
    }

    public BearingView getBearingView() {
        return bearingView;
    }

    public SORTView getSortView() {
        return sortView;
    }

    public NAMEView getNameView() {
        return nameView;
    }

    public DISPView getDispView() {
        return dispView;
    }

    public void clearCurrentShip() {
        currentShip = null;
    }

    // =====================================================
    // 画面遷移 API（Controller からの Card 切替）
    // =====================================================
    // 入力解釈結果に応じて CardLayout の表示を切り替える。
    // Controller は「どの画面に行くか」だけを決め、
    // 実際の UI 操作は AISMain2 が担う。
    public void showListSub() {
        showCard(ScreenId.LIST_SUB);
    }

    public void showOtherDetailSub() {
        showCard(ScreenId.OTHER_DETAIL_SUB);
    }

    public void showVoyageSub() {
        showCard(ScreenId.VOYAGE_SUB);
    }

    public void showMenu() {
        showCard(ScreenId.MENU);
    }

    public void showMessageMenu() {
        showCard(ScreenId.MESSAGE);
        messageMenuView.setSelectedIndex(0);
        messageMenuView.requestFocus();
    }

    public void showEditAndTxSubMenu() {
        showCard(ScreenId.EDIT_AND_TX_SUB);
    }

    public void showEditAndTx() {
        showCard(ScreenId.EDIT_AND_TX);
        editAndTxView.refresh();
    }

    public void showTextEdit() {

        // ① maxLength を毎回再計算
        int maxLen = calcTextMaxLength();

        // ② Model を再生成（最も安全）
        textEditModel = new TextEditModel(maxLen);

        // ③ View に Model を差し替え（再生成しない）
        textEditView.setModel(textEditModel);

        // ④ 初期化
        textEditView.resetCursor();
        textEditView.refresh();

        // ⑤ 表示
        showCard(ScreenId.TEXT_EDIT);
    }

    public void showTxConfirm() {
        showCard(ScreenId.EDIT_AND_TX_TX_CONFIRM);
    }

    public void showTxtransmitting() {
        showCard(ScreenId.EDIT_AND_TX_TX_TRANSMITTING);
    }

    public void showTxTray() {
        txTrayListView.updateFromModel(txTrayModel);
        showCard(ScreenId.TX_TRAY);
    }

    public void showTxTraySubMenu() {
        showCard(ScreenId.TX_TRAY_SUB);
    }

    public void showTxTransmitting() {
        showCard(ScreenId.EDIT_AND_TX_TX_TRANSMITTING);
    }

    public void showTxResult(TxMessage msg) {

        TxResultView view = getTxResultView();

        if (msg.isAddressed()) {
            view.setResultText("RESULT : ACT OK");
        } else {
            view.setResultText("TRANSMIT : OK");
        }

        showCard(ScreenId.EDIT_AND_TX_TX_RESULT);
    }


    public void showRxTray() {

        // ★ 初回のみダミー投入したい場合
        if (rxTrayModel.size() == 0) {
            rxTrayModel.generateDummyMessages();
        }

        rxTrayListView.updateFromModel(rxTrayModel);
        showCard(ScreenId.RX_TRAY);
    }

    public void showRxTraySubMenu() {
        rxTraySubMenuView.setSelectedIndex(0);
        showCard(ScreenId.RX_TRAY_SUB);
    }

    public void showRxTrayText() {
        int idx = rxTrayListView.getSelectedIndex();
        if (idx < 0) {
            return;
        }

        RxMessage msg = rxTrayModel.get(idx);
        if (msg == null) {
            return;
        }

        // ★ TEXT VIEW 遷移時に既読化
        msg.markRead();

        // ★ 一覧表示を更新（＊を消す）
        rxTrayListView.updateFromModel(rxTrayModel);

        // TEXT VIEW 表示
        rxTrayTextView.setMessage(msg);
        showCard(ScreenId.RX_TRAY_TEXT);
    }


    public void showRxTrayDetail() {
        int idx = rxTrayListView.getSelectedIndex();
        if (idx < 0) {
            return;
        }

        RxMessage msg = rxTrayModel.get(idx);
        rxTrayDetailView.setMessage(msg);

        showCard(ScreenId.RX_TRAY_DETAIL);
    }


    public void showInterrogation() {
        showCard(ScreenId.INTERROGATION);
    }

    public void showInterrogationNew() {
        interrogationModel.reset();
        interrogationView.refresh();
        showCard(ScreenId.INTERROGATION);
    }

    public void showInterrogationSUB() {
        showCard(ScreenId.INTERROGATION_SUB);
    }

    public void showInterrogationResponse() {
        showInterrogation();
    }

    public void showLongRange() {
        /* LONG-RANGE の画面表示 */ }

    public void showMaintenance() {
        maintenanceMenuView.setSelectedIndex(0);
        showCard(ScreenId.MAINTENANCE_MENU);
    }

    public void showSelfDiagnosis(){
        selfDiagnosisModel.reset();          // 状態を初期化
        selfDiagnosisView.update(selfDiagnosisModel);  // ← これが必須
        showCard(ScreenId.SELF_DIAGNOSIS);
    }

    public void showTransponderLog() {
        showCard(ScreenId.TRANSPONDER_LOG);
    }

    public void showControllerLog() {
        showCard(ScreenId.CONTROLLER_LOG);
    }

    public void showControllerLanLog() {       
        showCard(ScreenId.CONTROLLER_LAN_LOG);
    }

    public void showCommunicationTest() {
        communicationTestModel.reset();
        showCard(ScreenId.COMMUNICATION_TEST);
    }
    
    public void showCommunicationTestTx() {
        showCard(ScreenId.COMMUNICATION_TEST_TX);
    }

    public void showAISAlarmCurrent() {
        showCard(ScreenId.AIS_ALARM);
    }

    public void showAISAlarmHistory() {
        showCard(ScreenId.AIS_ALARM);
    }


    public void showGraphic(int mmsi) {

        OwnShipInfo own = shipManager.getOwnShip();
        ShipInfo target = shipManager.getShips().get(mmsi);

        graphicView.setTargetShip(own, target);
        showCard(ScreenId.GRAPHIC);   // 既存の CardLayout 切替
    }


    public void showPosnTime() {
        showCard(ScreenId.POSN_TIME);
    }

    public void showList() {
        showCard(ScreenId.LIST);
    }

    public void showOtherDetail() {
        showCard(ScreenId.OTHER_DETAIL);
    }

    public void showBearing() {
        showCard(ScreenId.BEARING);
    }

    public void showSort() {
        showCard(ScreenId.SORT);
    }

    public void showName() {
        showCard(ScreenId.NAME);
    }

    public void showDisp() {
        showCard(ScreenId.DISP);
    }

    public void showVoyage() {
        // ★ 編集セッション開始（毎回新規）
        voyageSession = new VoyageEditSession(myship);

        // ★ VoyageView を「作業用データ」で初期化
        voyageView.refreshFrom(voyageSession.getWorking());
        showCard(ScreenId.VOYAGE);
    }

    // ★ 再表示専用（絶対に new しない）
    public void redrawVoyage() {
        voyageView.refreshFrom(voyageSession.getWorking());
        showCard(ScreenId.VOYAGE);
    }

    public void showDestination(){
        showCard(ScreenId.DESTINATION);
    }

    public void showDestinationLoad() {

        // ① Model にデータ構築を任せる
        destinationLoadModel.buildFrom(voyageSession.getWorking());

        // ② View には「完成済みの List」だけ渡す
        destinationLoadView.setDestinations(
                destinationLoadModel.getDisplayList()
        );

        // ③ 画面遷移
        showCard(ScreenId.DESTINATION_LOAD);
    }



    public void showDraught() {
        showCard(ScreenId.DRAUGHT);
    }

    public void showPersons() {
        showCard(ScreenId.PERSONS);
        personsInputView.requestFocus();
    }

    public void showText() {
        showCard(ScreenId.TEXT);
    }

    public void showOwnDetail1() {
        pageIndex = 1;
        ownShipDetail1View.updateownShipPage1(myship, pageIndex);
        showCard(ScreenId.OWN_DETAIL1);
    }

    public void showOwnDetail2() {
        pageIndex = 1;
        ownShipDetail2View.updateownShipPage2(myship, pageIndex);
        showCard(ScreenId.OWN_DETAIL2);
    }

    public void showOwnTRX() {
        pageIndex = 1;
        ownShipTRXView.updateowntrxPage(myship, pageIndex);
        showCard(ScreenId.OWN_TRX);
    }

    /* ===== shipList ===== */
    public void selectPrevShip() {
        int sel = shipListView.getTable().getSelectedRow();
        if (sel > 0) {
            shipListView.getTable().changeSelection(sel - 1, 0, false, false);
        }
    }

    public void selectNextShip() {
        int sel = shipListView.getTable().getSelectedRow();
        int max = shipListView.getTable().getRowCount() - 1;
        if (sel < max) {
            shipListView.getTable().changeSelection(sel + 1, 0, false, false);
        }
    }

    /* ===== menu ===== */
    public void selectPrevMenu() {
        int idx = menuView.getSelectedIndex();
        if (idx > 0) {
            menuView.setSelectedIndex(idx - 1);
            menuView.requestFocusInWindow();
        }
    }

    public void selectNextMenu() {
        int idx = menuView.getSelectedIndex();
        int max = menuView.getItemCount() - 1;
        if (idx < max) {
            menuView.setSelectedIndex(idx + 1);
            menuView.requestFocusInWindow();
        }
    }

    /* ===== listSub ===== */
    public void selectPrevListSub() {
        int idx = listSubView.getSelectedIndex();
        if (idx > 0) {
            listSubView.setSelectedIndex(idx - 1);
            listSubView.requestFocusInWindow();
        }
    }

    public void selectNextListSub() {
        int idx = listSubView.getSelectedIndex();
        int max = listSubView.getItemCount() - 1;
        if (idx < max) {
            listSubView.setSelectedIndex(idx + 1);
            listSubView.requestFocusInWindow();
        }
    }

    // =====================================================
    // ENTER 実行 API（画面固有処理）
    // =====================================================
    // ENTER 押下時の「確定動作」を画面単位で提供する。
    // Controller は currentCard を見て
    // 対応する API を呼び分ける。
    
    public void enterMenu() {
        String sel = menuView.getSelectedValue();
        if (sel == null) {
            return;
        }

        if (sel.contains("EXIT")) {
            showList();
            return;
        }

        if (sel.contains("MESSAGE")) {
            showMessageMenu();
            return;
        }

        if (sel.contains("VOYAGE DATA")) {
            showVoyage();
            return;
        }

        if (sel.contains("MAINTENANCE")) {
            showMaintenance();
            return;
        }

        if (sel.contains("LINE MONITOR")) {
            showText();
            return;
        }

        showList();
    }

    
    public void enterTxTrayList() {
        int idx = txTrayListView.getSelectedIndex();
        if (idx < 0) {
            return;
        }

        TxMessage msg = txTrayModel.get(idx);
        txTextView.setMessage(msg);
        showCard(ScreenId.TX_TRAY_TEXT);
    }

    public void enterTxTraySubMenu() {
        int idx = txTrayListView.getSelectedIndex();
        TxMessage msg = txTrayModel.get(idx);

        switch (txTraySubMenuView.getSelectedIndex()) {
            case 0: // DETAIL VIEW
                txMessageDetailView.setMessage(msg);
                showCard(ScreenId.TX_TRAY_DETAIL);
                break;
            case 1: // EDIT
                editAndTxModel.loadFromTxMessage(msg);
                showEditAndTx();
                break;
            case 2: // DELETE
                txTrayModel.remove(idx);
                showTxTray();
                break;
            case 3: // EXIT
                showTxTray();
                break;
        }
    }

    public void enterRxTrayList() {
        showRxTrayText();
    }

    public void enterRxTraySubMenu() {
        int idx = rxTrayListView.getSelectedIndex();
        RxMessage msg = rxTrayModel.get(idx);

        switch (rxTraySubMenuView.getSelectedIndex()) {
            case 0: // DETAIL VIEW
                rxTrayDetailView.setMessage(msg);
                showCard(ScreenId.RX_TRAY_DETAIL);
                break;

            case 1: // EDIT
                editAndTxModel.loadFromRxMessage(msg);
                showEditAndTx();
                break;

            case 2: // DELETE
                rxTrayModel.remove(idx);
                showRxTray();
                break;

            case 3: // EXIT
                showRxTray();
                break;
        }
    }



    public void enterShipList() {
        int row = shipListView.getTable().getSelectedRow();
        if (row < 0) {
            return;
        }

        int mmsi = (int) shipListView.getTableModel().getValueAt(row, 4);
        ShipInfo ship = shipManager.getShips().get(mmsi);
        if (ship == null) {
            return;
        }

        // ★ 自船を取得
        OwnShipInfo own = shipManager.getOwnShip();
        currentShip = ship;
        otherShipPager = new Paginator(1, 9);
        pageIndex = 1;
        updateOtherShipPage(own, currentShip, pageIndex);
        showOtherDetail();
    }

    public void enterListSub() {
        String sel = listSubView.getSelectedItem();
        if (sel == null) {
            return;
        }

        if (sel.contains("EXIT")) {
            showList();
            return;
        }

        if (sel.contains("BEARING")) {
            showBearing();
            return;
        }

        if (sel.contains("SORT")) {
            showSort();
            return;
        }

        if (sel.contains("NAME")) {
            showName();
            return;
        }

        if (sel.contains("DISP")) {
            showDisp();
        }
    }

    public void enterOtherDetailSub() {
        String sub = otherShipSubMenuView.getSelectedItem();
        if ("[ EXIT ]".equals(sub)) {
            showCard(ScreenId.LIST);
            currentShip = null;
        }
    }

    public void enterVoyage() {
        settingItem vsel = voyageView.getList().getSelectedValue();
        if (vsel == null) {
            return;
        }

        switch (vsel.id) {
            case 1:
                showCard(ScreenId.NAV_STATUS);
                navStatusView.setSelectedIndex(0);
                break;
            case 2: // DESTINATION
                showCard(ScreenId.DESTINATION);
                destinationInputView.resetCursor();
                destinationInputView.requestFocusInWindow();
                break;
            case 3: // ETA
                showCard(ScreenId.ETA);
                break;
            case 4: // DRAUGHT
                showDraught();
                draughtInputView.requestFocusInWindow();
                break;

            case 5: // PERSONS ON BOARD
                showPersons();
                personsInputView.requestFocusInWindow();
                break;
            case 6: // SHIP TYPE U.S.
                showCard(ScreenId.SHIP_TYPE_US);
                shipTypeUSInputView.requestFocusInWindow();
                break;
            case 7: // TYPE OF SHIP / CARGO
                shipTypeCargoModel.resetPhase();
                showCard(ScreenId.SHIP_TYPE_CARGO);
                shipTypeCargoView.requestFocusInWindow();
                break;

        }
    }

    // =====================================================
    // ENTER 実行 API（NAV STATUS）
    // =====================================================
    public void enterNavStatus() {

        String status = navStatusView.getSelectedStatus();
        if (status == null) {
            return;
        }

        // データ更新
        voyageSession.getWorking().setNavStatus(NavStatus.fromLabel(status));

        // VoyageView 表示更新
        voyageView.updateNavStatus(status);

        // VOYAGE DATA に戻る
        redrawVoyage();

        // カーソルを 2.DESTINATION へ
        SwingUtilities.invokeLater(()
                -> getVoyageView().selectItemById(2)
        );
    }



    public void enterVoyageSub() {

        String vsub = voyageSubView.getSelectedItem();
        if (vsub == null) {
            return;
        }

        // ===== SET =====
        if ("[ SET ]".equals(vsub)) {

            // ① 航海情報を確定
            voyageSession.commit();

            // ② 確定済み OwnShipInfo を取得
            OwnShipInfo ownShip = shipManager.getOwnShip();

            // JSON生成
            String json = OwnShipJsonUtil.toVoyageJson(ownShip);

            try {
                // ④ UDP ブロードキャスト送信
                AisUdpBroadcaster broadcaster = new AisUdpBroadcaster();

                broadcaster.sendJson(json);

                broadcaster.close();

            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Failed to send voyage update", e);
            }

            // （デバッグ表示を残すなら）
            LOGGER.fine(() -> "JSON sent: " + json);

            voyageSession = null;
            showCard(ScreenId.MENU);
            return;
        }



        // ===== DEST LOAD =====
        if( "[ DEST LOAD ]".equals(vsub)) {
            showDestinationLoad();
            return;
        }

        // ===== EXIT =====
        if ("[ EXIT ]".equals(vsub)) {

            // ★ 何もせず破棄
            voyageSession = null;
            showCard(ScreenId.MENU);
            return;
        }
    }

    public void discardVoyageSession() {
        voyageSession = null;
    }


    

    public void selectNextVoyageItem() {
        int sv = voyageView.getSelectedIndex();
        int target = (sv == -1) ? 0 : sv + 1;
        int size = voyageView.getItemCount();

        while (target < size) {
            settingItem it = voyageView.getList().getModel().getElementAt(target);
            if (it.selectable) {
                voyageView.setSelectedIndex(target);
                voyageView.getList().ensureIndexIsVisible(target);
                break;
            }
            target++;
        }
    }

    public void selectPrevVoyageItem() {
        int sv = voyageView.getSelectedIndex();
        int target = (sv == -1) ? voyageView.getItemCount() - 1 : sv - 1;

        while (target >= 0) {
            settingItem it = voyageView.getList().getModel().getElementAt(target);
            if (it.selectable) {
                voyageView.setSelectedIndex(target);
                voyageView.getList().ensureIndexIsVisible(target);
                break;
            }
            target--;
        }
    }

    public void nextOtherDetailPage() {
        int newPage = otherShipPager.next();
        updateOtherShipPage(shipManager.getOwnShip(), currentShip, newPage);
    }

    public void prevOtherDetailPage() {
        int newPage = otherShipPager.prev();
        updateOtherShipPage(shipManager.getOwnShip(), currentShip, newPage);
    }

    public void nextOwnDetail1Page() {
        int newPage = ownShipPager1.next();
        ownShipDetail1View.updateownShipPage1(myship, newPage);
    }

    public void nextOwnDetail2Page() {
        int newPage = ownShipPager2.next();
        ownShipDetail2View.updateownShipPage2(myship, newPage);
    }

    public void nextOwnTRXPage() {
        int newPage = trxPager.next();
        ownShipTRXView.updateowntrxPage(myship, newPage);
    }

    public void prevOwnDetail1Page() {
        int newPage = ownShipPager1.prev();
        ownShipDetail1View.updateownShipPage1(myship, newPage);
    }

    public void prevOwnDetail2Page() {
        int newPage = ownShipPager2.prev();
        ownShipDetail2View.updateownShipPage2(myship, newPage);
    }

    public void prevOwnTRXPage() {
        int newPage = trxPager.prev();
        ownShipTRXView.updateowntrxPage(myship, newPage);
    }

    private void initViewActions() {

        destinationInputView.setListener(new DestinationInputView.DestinationInputListener() {

            @Override
            public void onOk(String destination) {

                // ★ ① working を更新
                voyageSession.getWorking().destination = destination;

                // ★ ② 表示更新
                voyageView.setDestination(destination);

                // ★ ③ VOYAGE へ戻る
                redrawVoyage();
                voyageView.selectEta();
            }

            @Override
            public void onExit() {
                redrawVoyage();
                voyageView.selectEta();
            }
        });

        textEditView.setListener(new TextEditView.Listener() {

            @Override
            public void onOk(String text) {

                // ① 編集結果を EditAndTxModel に確定
                editAndTxModel.setText(text);

                // ② SUB MENU に戻る
                showEditAndTxSubMenu();
            }

            @Override
            public void onExit() {

                // 編集破棄（Model はそのまま捨てる）
                showEditAndTxSubMenu();
            }
        });



    }

    private int calcTextMaxLength() {

        EditAndTxModel.Format f = editAndTxModel.getFormat();
        EditAndTxModel.Category c = editAndTxModel.getCategory();

        if (f == EditAndTxModel.Format.ADDRESSED) {
            return (c == EditAndTxModel.Category.SAFETY) ? 156 : 151;
        } else {
            return (c == EditAndTxModel.Category.SAFETY) ? 161 : 156;
        }
    }

    /* =====================================================
    * TX MESSAGE 生成
    * ===================================================== */
    public TxMessage buildTxMessageFromEdit() {

        TxMessage msg = new TxMessage();

        /* ===== メッセージ種別 ===== */
        msg.format = editAndTxModel.getFormat();
        msg.category = editAndTxModel.getCategory();
        msg.reply = editAndTxModel.getReply() == EditAndTxModel.Reply.ON;
        msg.function = editAndTxModel.getFunction().name();
        msg.channel = editAndTxModel.getChannel().ordinal();

        /* ===== 宛先 ===== */
        if (msg.format == EditAndTxModel.Format.ADDRESSED) {
            msg.destination = editAndTxModel.getMmsiText(); // ★ 9桁文字列
        } else {
            msg.destination = "BROADCAST";
        }

        /* ===== 本文 ===== */
        msg.text = editAndTxModel.getText();

        /* ===== SAVE 状態 ===== */
        // utcDateTime / transmitted / result は TxMessage コンストラクタで初期化済み
        return msg;
    }

    /* =====================================================
    * TX MESSAGE SAVE
    * ===================================================== */
    public void saveCurrentTxMessage() {

        TxMessage msg = buildTxMessageFromEdit();

        txTrayModel.add(msg);

        // 一覧へ
        showTxTray();
    }

    public void executePseudoTx() {

        TxMessage msg = editAndTxModel.buildTxMessage();

        // TX TRAY に追加（未送信→送信中へ）
        txTrayModel.add(msg);

        executePseudoTx(msg);
    }

    public void executePseudoTx(TxMessage msg) {

        // ===== TX 開始 =====
        msg.markTransmitting();

        showTxTransmitting(); // NOW TRANSMITTING...

        // ===== 擬似 ACK（1.5 秒後）=====
        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {

            msg.markAckOk();

            showTxResult(msg); // ★ これだけ
        });

        timer.setRepeats(false);
        timer.start();
    }

    private boolean pseudoInterrogationAck() {
        return Math.random() > 0.2; // 80% 成功
    }

    public void executeInterrogationTx() {

        interrogationModel.startTx();
        showCard(ScreenId.INTERROGATION_TX);

        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {

            boolean ok = pseudoInterrogationAck(); // 擬似判定
            interrogationModel.setTxResult(ok);

            interrogationTxResultView.setResult(ok);
            showCard(ScreenId.INTERROGATION_TX_RESULT);

        });

        timer.setRepeats(false);
        timer.start();
    }

    public void executeCommunicationtest() {
        
        communicationTestModel.startTest();
        showCard(ScreenId.COMMUNICATION_TEST_TX);

        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {

            boolean ok = pseudoInterrogationAck();
            communicationTestModel.setResult(ok);

            communicationAckPopupView.setAckResult(ok);
            showCard(ScreenId.COMMUNICATION_ACK_POPUP);

        });
        timer.setRepeats(false);
        timer.start();
    }


    // ===== 右クリック説明生成 =====
    private static RoundButton createButtonWithRightPane(String label, String description, RightPaneController rightPaneController) {
        final RoundButton rb = new RoundButton(label);
        final String desc = (description == null) ? "説明はありません" : description;
        rb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isPopupTrigger()) {
                    if (lastButton == rb) {
                        rightPaneController.clear();
                        lastButton = null;
                        return;
                    }
                    String extra = null;
                    if ("PWR".equals(label)) {
                        extra = "電源オフにはパスワード入力が必要な機種があります。";
                    } else if ("DIM".equals(label)) {
                        extra = "バックライトを最小にすると夜間で視認困難になる場合があります。";
                    }
                    rightPaneController.showButtonHelp(label, desc, extra);
                    rb.requestFocusInWindow();
                    lastButton = rb;
                }
            }
        });
        return rb;
    }

    // 他船一覧表示の更新処理
    private void refreshTable() {

        // ① 現在の選択行を保持
        int selected = shipListView.getTable().getSelectedRow();

        OwnShipInfo own = shipManager.getOwnShip();
        double ownLat = own.lat;
        double ownLon = own.lon;

        // ② ShipInfo をコピーして距離順にソート
        java.util.List<ShipInfo> ships
                = new java.util.ArrayList<>(shipManager.getShips().values());

        ships.sort(Comparator.comparingDouble(
                other -> NavigationUtil.calcRangeNm(
                        ownLat, ownLon, other.lat, other.lon)
        ));

        // ③ テーブル行データを生成
        java.util.List<Object[]> rows = new java.util.ArrayList<>();

        for (ShipInfo other : ships) {

            double brg = NavigationUtil.calcBearingDeg(
                    ownLat, ownLon, other.lat, other.lon);

            double rng = NavigationUtil.calcRangeNm(
                    ownLat, ownLon, other.lat, other.lon);

            String etStr = String.format("%.0f", other.getETmin());
            String name = (other.vesselName != null ? other.vesselName : "");

            rows.add(new Object[]{
                String.format("%.0f", brg),
                String.format("%.2f", rng),
                etStr,
                name,
                other.mmsi
            });
        }

        // ④ View に丸投げ
        shipListView.updateTable(rows, selected);
    }

    // 他船一覧表示の中身
    private void updateOtherShipPage(OwnShipInfo own, ShipInfo ship, int page) {
        otherShipDetailView.showPage(own, ship, page);
    }

// カードを切り替える共通メソッド
    private void showCard(ScreenId screenId) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, screenId.cardName());
        currentScreen = screenId;

        if (rightPaneController != null) {
            rightPaneController.showCardHelp(screenId);
        }
        
        SwingUtilities.invokeLater(() -> {
            switch (screenId) {
                case LIST:
                    shipListView.getTable().requestFocusInWindow();
                    break;
                case MENU:
                    menuView.requestFocusInWindow();
                    break;
                case VOYAGE:
                    for (int i = 0; i < voyageView.getItemCount(); i++) {
                        if (voyageView.getList().getModel().getElementAt(i).selectable) {
                            voyageView.setSelectedIndex(i);
                            break;
                        }
                    }
                    voyageView.getList().requestFocusInWindow();
                    break;
                case OTHER_DETAIL_SUB:
                    otherShipSubMenuView.requestFocusInWindow();
                    break;
                case TEXT:
                    textArea.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AISMain2();
        });
    }
}
