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
import ais.model.OwnShipInfo;
import ais.model.PersonsInputModel;
import ais.model.RxTrayModel;
import ais.model.SelfDiagnosisModel;
import ais.model.ShipInfo;
import ais.model.ShipManager;
import ais.model.ShipTypeCargoInputModel;
import ais.model.ShipTypeUSInputModel;
import ais.model.TextEditModel;
import ais.model.TxMessage;
import ais.model.TxTrayModel;
import ais.model.VoyageEditSession;
import ais.network.UdpReceiver;
import ais.util.NavigationUtil;
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
import ais.workflow.MaintenanceWorkflow;
import ais.workflow.MessageWorkflow;
import ais.workflow.VoyageWorkflow;
import java.awt.*;
import java.util.*;
import javax.swing.*;




public class AISMain2 {
    /* =====================================================
     * 定数・通信関連
     * ===================================================== */
    private static final int PORT = 17020;
    private UdpReceiver udpReceiver;

    /* =====================================================
     * フレーム・レイアウト基盤
     * ===================================================== */
    private MainFrame mainFrame;
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
    private MaintenanceWorkflow maintenanceWorkflow;
    private MessageWorkflow messageWorkflow;
    private VoyageWorkflow voyageWorkflow;

    /* =====================================================
     * 右ペイン（説明表示）
     * ===================================================== */
    private RightPaneController rightPaneController;
    private ScreenNavigator screenNavigator;

    /* =====================================================
     * データ管理
     * ===================================================== */
    private final ShipManager shipManager;
    private OwnShipInfo myship;

    /* =====================================================
     * 画面状態管理
     * ===================================================== */

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

        this.mainFrame = new MainFrame("AISコントローラ");

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
        ScreenRegistry screenRegistry = new ScreenRegistry(cardPanel);

        // ★従来のテキストエリア（Card1 として登録）
        screenRegistry.register(ScreenId.TEXT, scrollPane);

        myship = ShipManager.getInstance().getOwnShip();


        // =====================================================
        // 各画面 View インスタンス生成
        // =====================================================
        // CardLayout に登録する各画面の View を生成する
        // （この時点では表示・遷移は行わない）
        initializeViews();

        // =====================================================
        // CardLayout への画面登録
        // =====================================================
        //画面名（card 名）と View を対応付けて登録する
        // 遷移判断は Controller 側で行う
        registerScreens(screenRegistry);
        screenNavigator = new ScreenNavigator(cardPanel, this::focusScreen);
        maintenanceWorkflow = new MaintenanceWorkflow(this);
        messageWorkflow = new MessageWorkflow(this);
        voyageWorkflow = new VoyageWorkflow(this);
        inputController = new InputController(this);



        // 初期表示はリストにする
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
        RightPanePanel rightPanel = new RightPanePanel();
        this.rightPaneController = rightPanel.getController();
        screenNavigator.setRightPaneController(rightPaneController);

        JPanel bottomLeft = ControlPanelFactory.create(inputController, rightPaneController);

        leftGbc.gridy = 2;
        leftGbc.weighty = 0.4;
        leftPanel.add(bottomLeft, leftGbc);

        // === Layout ===
        mainFrame.addLeftPanel(leftPanel);
        mainFrame.addRightPanel(rightPanel);
        mainFrame.show();

    }

    private void initializeViews() {
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

    }

    private void registerScreens(ScreenRegistry screenRegistry) {
        screenRegistry.register(ScreenId.LIST, shipListView);
        screenRegistry.register(ScreenId.MENU, menuView);
        screenRegistry.register(ScreenId.MESSAGE, messageMenuView);
        screenRegistry.register(ScreenId.EDIT_AND_TX, editAndTxView);
        screenRegistry.register(ScreenId.EDIT_AND_TX_SUB, editAndTxSubMenuView);
        screenRegistry.register(ScreenId.TEXT_EDIT, textEditView);
        screenRegistry.register(ScreenId.EDIT_AND_TX_TX_CONFIRM, txConfirmView);
        screenRegistry.register(ScreenId.EDIT_AND_TX_TX_TRANSMITTING, txTransmittingView);
        screenRegistry.register(ScreenId.EDIT_AND_TX_TX_RESULT, txResultView);
        screenRegistry.register(ScreenId.TX_TRAY, txTrayListView);
        screenRegistry.register(ScreenId.TX_TRAY_TEXT, txTextView);
        screenRegistry.register(ScreenId.TX_TRAY_SUB, txTraySubMenuView);
        screenRegistry.register(ScreenId.TX_TRAY_DETAIL, txMessageDetailView);
        screenRegistry.register(ScreenId.RX_TRAY, rxTrayListView);
        screenRegistry.register(ScreenId.RX_TRAY_SUB, rxTraySubMenuView);
        screenRegistry.register(ScreenId.RX_TRAY_TEXT, rxTrayTextView);
        screenRegistry.register(ScreenId.RX_TRAY_DETAIL, rxTrayDetailView);
        screenRegistry.register(ScreenId.INTERROGATION, interrogationView);
        screenRegistry.register(ScreenId.INTERROGATION_SUB, interrogationSubMenuView);
        screenRegistry.register(ScreenId.INTERROGATION_TX, interrogationTxView);
        screenRegistry.register(ScreenId.INTERROGATION_TX_RESULT, interrogationTxResultView);
        screenRegistry.register(ScreenId.MAINTENANCE_MENU, maintenanceMenuView);
        screenRegistry.register(ScreenId.SELF_DIAGNOSIS, selfDiagnosisView);
        screenRegistry.register(ScreenId.TRANSPONDER_LOG, transponderLogView);
        screenRegistry.register(ScreenId.CONTROLLER_LOG, controllerLogView);
        screenRegistry.register(ScreenId.CONTROLLER_LAN_LOG, controllerLanLogView);
        screenRegistry.register(ScreenId.COMMUNICATION_ACK_POPUP, communicationAckPopupView);
        screenRegistry.register(ScreenId.COMMUNICATION_TEST, communicationTestView);
        screenRegistry.register(ScreenId.COMMUNICATION_TEST_TX, communicationTxView);
        screenRegistry.register(ScreenId.AIS_ALARM, aisAlarmCurrentView);
        screenRegistry.register(ScreenId.VOYAGE, voyageView);
        screenRegistry.register(ScreenId.VOYAGE_SUB, voyageSubView);
        screenRegistry.register(ScreenId.NAV_STATUS, navStatusView);
        screenRegistry.register(ScreenId.OTHER_DETAIL, otherShipDetailView);
        screenRegistry.register(ScreenId.OTHER_DETAIL_SUB, otherShipSubMenuView);
        screenRegistry.register(ScreenId.GRAPHIC, graphicView);
        screenRegistry.register(ScreenId.OWN_DETAIL1, ownShipDetail1View);
        screenRegistry.register(ScreenId.OWN_DETAIL2, ownShipDetail2View);
        screenRegistry.register(ScreenId.OWN_TRX, ownShipTRXView);
        screenRegistry.register(ScreenId.POSN_TIME, PosnTimeView);
        screenRegistry.register(ScreenId.LIST_SUB, listSubView);
        screenRegistry.register(ScreenId.BEARING, bearingView);
        screenRegistry.register(ScreenId.SORT, sortView);
        screenRegistry.register(ScreenId.NAME, nameView);
        screenRegistry.register(ScreenId.DISP, dispView);
        screenRegistry.register(ScreenId.DESTINATION, destinationInputView);
        screenRegistry.register(ScreenId.ETA, etaInputView);
        screenRegistry.register(ScreenId.DRAUGHT, draughtInputView);
        screenRegistry.register(ScreenId.PERSONS, personsInputView);
        screenRegistry.register(ScreenId.SHIP_TYPE_US, shipTypeUSInputView);
        screenRegistry.register(ScreenId.SHIP_TYPE_CARGO, shipTypeCargoView);
        screenRegistry.register(ScreenId.DESTINATION_LOAD, destinationLoadView);
    }

    // ===== Getter メソッド群 =====
    public String getCurrentCard() {
        return screenNavigator.getCurrentCard();
    }

    public ScreenId getCurrentScreen() {
        return screenNavigator.getCurrentScreen();
    }

    public OwnShipInfo getOwnShipInfo() {
        return myship;
    }

    public ShipManager getShipManager() {
        return shipManager;
    }

    public VoyageWorkflow getVoyageWorkflow() {
        return voyageWorkflow;
    }

    public MessageWorkflow getMessageWorkflow() {
        return messageWorkflow;
    }

    public MaintenanceWorkflow getMaintenanceWorkflow() {
        return maintenanceWorkflow;
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

    public void setTextEditModel(TextEditModel textEditModel) {
        this.textEditModel = textEditModel;
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

    public TxTrayModel getTxTrayModel() {
        return txTrayModel;
    }

    public TextViewScreen getTxTextView() {
        return txTextView;
    }

    public TXMessageDetailView getTxMessageDetailView() {
        return txMessageDetailView;
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

    public RxTrayModel getRxTrayModel() {
        return rxTrayModel;
    }

    public RxTrayTextView getRxTrayTextView() {
        return rxTrayTextView;
    }

    public RxTrayDetailView getRxTrayDetailView() {
        return rxTrayDetailView;
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

    public CommunicationAckPopupView getCommunicationAckPopupView() {
        return communicationAckPopupView;
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

    public DestinationLoadModel getDestinationLoadModel() {
        return destinationLoadModel;
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
        return voyageWorkflow.getSession();
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
        messageWorkflow.showMessageMenu();
    }

    public void showEditAndTxSubMenu() {
        messageWorkflow.showEditAndTxSubMenu();
    }

    public void showEditAndTx() {
        messageWorkflow.showEditAndTx();
    }

    public void showTextEdit() {
        messageWorkflow.showTextEdit();
    }

    public void showTxConfirm() {
        messageWorkflow.showTxConfirm();
    }

    public void showTxtransmitting() {
        messageWorkflow.showTxTransmitting();
    }

    public void showTxTray() {
        messageWorkflow.showTxTray();
    }

    public void showTxTraySubMenu() {
        messageWorkflow.showTxTraySubMenu();
    }

    public void showTxTransmitting() {
        messageWorkflow.showTxTransmitting();
    }

    public void showTxResult(TxMessage msg) {
        messageWorkflow.showTxResult(msg);
    }

    public void showRxTray() {
        messageWorkflow.showRxTray();
    }

    public void showRxTraySubMenu() {
        messageWorkflow.showRxTraySubMenu();
    }

    public void showRxTrayText() {
        messageWorkflow.showRxTrayText();
    }

    public void showRxTrayDetail() {
        messageWorkflow.showRxTrayDetail();
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
        maintenanceWorkflow.showMaintenance();
    }

    public void showSelfDiagnosis(){
        maintenanceWorkflow.showSelfDiagnosis();
    }

    public void showTransponderLog() {
        maintenanceWorkflow.showTransponderLog();
    }

    public void showControllerLog() {
        maintenanceWorkflow.showControllerLog();
    }

    public void showControllerLanLog() {
        maintenanceWorkflow.showControllerLanLog();
    }

    public void showCommunicationTest() {
        maintenanceWorkflow.showCommunicationTest();
    }

    public void showCommunicationTestTx() {
        maintenanceWorkflow.showCommunicationTestTx();
    }

    public void showAISAlarmCurrent() {
        maintenanceWorkflow.showAISAlarmCurrent();
    }

    public void showAISAlarmHistory() {
        maintenanceWorkflow.showAISAlarmHistory();
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
        voyageWorkflow.showVoyage();
    }

    // ★ 再表示専用（絶対に new しない）
    public void redrawVoyage() {
        voyageWorkflow.redrawVoyage();
    }

    public void showDestination(){
        showCard(ScreenId.DESTINATION);
    }

    public void showDestinationLoad() {
        voyageWorkflow.showDestinationLoad();
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
        messageWorkflow.enterTxTrayList();
    }

    public void enterTxTraySubMenu() {
        messageWorkflow.enterTxTraySubMenu();
    }

    public void enterRxTrayList() {
        messageWorkflow.enterRxTrayList();
    }

    public void enterRxTraySubMenu() {
        messageWorkflow.enterRxTraySubMenu();
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
        voyageWorkflow.enterVoyage();
    }

    // =====================================================
    // ENTER 実行 API（NAV STATUS）
    // =====================================================
    public void enterNavStatus() {
        voyageWorkflow.enterNavStatus();
    }



    public void enterVoyageSub() {
        voyageWorkflow.enterVoyageSub();
    }

    public void discardVoyageSession() {
        voyageWorkflow.discardSession();
    }




    public void selectNextVoyageItem() {
        voyageWorkflow.selectNextVoyageItem();
    }

    public void selectPrevVoyageItem() {
        voyageWorkflow.selectPrevVoyageItem();
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
                getVoyageSession().getWorking().destination = destination;

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

                messageWorkflow.confirmTextEdit(text);
            }

            @Override
            public void onExit() {

                messageWorkflow.exitTextEdit();
            }
        });



    }

    private int calcTextMaxLength() {
        return messageWorkflow.calcTextMaxLength();
    }

    /* =====================================================
    * TX MESSAGE 生成
    * ===================================================== */
    public TxMessage buildTxMessageFromEdit() {
        return messageWorkflow.buildTxMessageFromEdit();
    }

    /* =====================================================
    * TX MESSAGE SAVE
    * ===================================================== */
    public void saveCurrentTxMessage() {
        messageWorkflow.saveCurrentTxMessage();
    }

    public void executePseudoTx() {
        messageWorkflow.executePseudoTx();
    }

    public void executePseudoTx(TxMessage msg) {
        messageWorkflow.executePseudoTx(msg);
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
        maintenanceWorkflow.executeCommunicationTest();
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
                        ownLat, ownLon, other.getLat(), other.getLon())
        ));

        // ③ テーブル行データを生成
        java.util.List<Object[]> rows = new java.util.ArrayList<>();

        for (ShipInfo other : ships) {

            double brg = NavigationUtil.calcBearingDeg(
                    ownLat, ownLon, other.getLat(), other.getLon());

            double rng = NavigationUtil.calcRangeNm(
                    ownLat, ownLon, other.getLat(), other.getLon());

            String etStr = String.format("%.0f", other.getETmin());
            String name = other.getVesselName();

            rows.add(new Object[]{
                String.format("%.0f", brg),
                String.format("%.2f", rng),
                etStr,
                name,
                other.getMmsi()
            });
        }

        // ④ View に丸投げ
        shipListView.updateTable(rows, selected);
    }

    // 他船一覧表示の中身
    private void updateOtherShipPage(OwnShipInfo own, ShipInfo ship, int page) {
        otherShipDetailView.showPage(own, ship, page);
    }

    public void navigateTo(ScreenId screenId) {
        showCard(screenId);
    }

// カードを切り替える共通メソッド
    private void showCard(ScreenId screenId) {
        screenNavigator.show(screenId);
    }

    private void focusScreen(ScreenId screenId) {
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
