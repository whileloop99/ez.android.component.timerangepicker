package ez.android.timerangepicker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lombok.Getter
import lombok.Setter
import org.threeten.bp.LocalTime

@Getter
@Setter
class TimeRangePickerBottomSheetFragmentViewModel : ViewModel() {
    val start = MutableLiveData<LocalTime>()
    val end = MutableLiveData<LocalTime>()
}