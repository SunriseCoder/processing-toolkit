package app.tools.forms;

import java.io.File;

import app.core.dto.TrackMetadata;
import app.core.dto.VideoFileMetadata;
import app.utils.FFMpegUtils;
import app.utils.FXUtils;
import app.utils.FormattingUtils;
import app.utils.MathUtils;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class BrandingFormV1 extends VBox {
    @FXML
    private TextField inputFileControl;
    @FXML
    private TableView<TrackMetadata> inputFileMetadataTable;

    private FileChooser inputFileChooser;

    private boolean alreadyRunning;

    public BrandingFormV1() {
        FXUtils.loadFXML(this);
        createInputFileMetadataTableColumns();
        inputFileChooser = new FileChooser();
    }

    private void createInputFileMetadataTableColumns() {
        createInputFileMetadataTableColumn("Id",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("index")));
        createInputFileMetadataTableColumn("Type",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("codec_type")));
        createInputFileMetadataTableColumn("Codec",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("codec_name")));
        createInputFileMetadataTableColumn("Pixel Format",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("pix_fmt")));
        createInputFileMetadataTableColumn("Resolution",
                cellData -> new ReadOnlyStringWrapper(
                        cellData.getValue().getParameter("width") == null ? "" :
                            cellData.getValue().getParameter("width") + "x" +
                            cellData.getValue().getParameter("height")));
        createInputFileMetadataTableColumn("FPS",
                cellData -> new ReadOnlyStringWrapper(
                        cellData.getValue().getParameter("r_frame_rate") == null
                        || cellData.getValue().getParameter("r_frame_rate").equals("0/0") ? "" :
                                cellData.getValue().getParameter("r_frame_rate") + "(" +
                                cellData.getValue().getParameter("avg_frame_rate") + ")"));
        createInputFileMetadataTableColumn("Duration",
                cellData -> new ReadOnlyStringWrapper(
                    FormattingUtils.humanReadableTimeS(
                        MathUtils.roundToLong(
                            Double.parseDouble(
                                cellData.getValue().getParameter("duration")
                            )
                        )
                    )
                )
        );

        createInputFileMetadataTableColumn("Frequency",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("sample_rate")));
        createInputFileMetadataTableColumn("Channels",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("channels")));
        createInputFileMetadataTableColumn("Layout",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("channel_layout")));

        createInputFileMetadataTableColumn("Bitrate",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("bit_rate")));

        createInputFileMetadataTableColumn("Language",
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getParameter("TAG:language")));
    }

    private void createInputFileMetadataTableColumn(String columnName,
            Callback<CellDataFeatures<TrackMetadata, String>, ObservableValue<String>> lambda) {
        TableColumn<TrackMetadata, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(lambda);
        inputFileMetadataTable.getColumns().add(column);
    }

    @FXML
    private void browseInputFile() {
        File file = inputFileChooser.showOpenDialog(null);
        if (file != null) {
            inputFileControl.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void inputFileChanged() {
        String inputFilename = inputFileControl.getText();
        File inputFile = new File(inputFilename);
        if (inputFile.exists()) {
            inputFileControl.setStyle("-fx-text-fill: green;");
            probeInputFile(inputFile);
        } else {
            inputFileControl.setStyle("-fx-text-fill: red;");
        }
    }

    private void probeInputFile(File inputFile) {
        // Checking that the process is not running already
        if (alreadyRunning) {
            return;
        }
        alreadyRunning = true;

        System.out.println("Processing Input File: " + inputFile.getAbsolutePath());
        VideoFileMetadata metadata = FFMpegUtils.getVideoFileMetadata(inputFile.getAbsolutePath());
        inputFileMetadataTable.getItems().clear();
        inputFileMetadataTable.getItems().addAll(metadata.getTracks());

        alreadyRunning = false;
    }
}
