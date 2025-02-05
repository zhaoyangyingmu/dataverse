package edu.harvard.iq.dataverse;

import edu.harvard.iq.dataverse.util.BundleUtil;
import edu.harvard.iq.dataverse.util.file.CreateDataFileResult;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author adaybujeda
 */
@Stateless
public class EditDataFilesPageHelper {

    public static final String MAX_ERRORS_TO_DISPLAY_SETTING = ":CreateDataFilesMaxErrorsToDisplay";
    public static final Integer MAX_ERRORS_TO_DISPLAY = 5;

    @Inject
    private SettingsWrapper settingsWrapper;

    public String getHtmlErrorMessage(CreateDataFileResult createDataFileResult) {
        List<String> errors = createDataFileResult.getErrors();
        if(errors == null || errors.isEmpty()) {
            return null;
        }

        Integer maxErrorsToShow = settingsWrapper.getInteger(EditDataFilesPageHelper.MAX_ERRORS_TO_DISPLAY_SETTING, EditDataFilesPageHelper.MAX_ERRORS_TO_DISPLAY);
        if(maxErrorsToShow < 1) {
            return null;
        }

        String typeMessage = Optional.ofNullable(BundleUtil.getStringFromBundle(createDataFileResult.getBundleKey())).orElse("Error processing file");
        String errorsMessage = errors.stream().limit(maxErrorsToShow).map(text -> String.format("<li>%s</li>", text)).collect(Collectors.joining());
        return String.format("%s:<br /><ul>%s</ul>", typeMessage, errorsMessage);
    }
}
