package ez.android.timerangepicker

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ez.android.timerangepicker.databinding.FragmentBottomSheetTimerangepickerBinding
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.experimental.Accessors
import org.threeten.bp.LocalTime

@Accessors(prefix = ["m"])
@Getter
@Setter
@NoArgsConstructor
class TimeRangePickerBottomSheetFragment(private val mStartTime: LocalTime, private val mEndTime: LocalTime) : BottomSheetDialogFragment() {
    private var mBinding: FragmentBottomSheetTimerangepickerBinding? = null
    private var mViewModel: TimeRangePickerBottomSheetFragmentViewModel? = null
    private val mCallback: Callback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialog1: DialogInterface? ->
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.setDraggable(false)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_timerangepicker, container, false)
        mBinding!!.setHandler(this)
        mBinding!!.setLifecycleOwner(this)
        mViewModel = ViewModelProvider(this).get(TimeRangePickerBottomSheetFragmentViewModel::class.java)
        mViewModel!!.start.value = mStartTime
        mViewModel!!.end.value = mEndTime
        mBinding!!.setViewModel(mViewModel)
        mBinding!!.timePicker.setTime(mViewModel!!.start.value!!, mViewModel!!.end.value!!)
        mBinding!!.timePicker.listener = { start: LocalTime?, end: LocalTime? ->
            mViewModel!!.start.setValue(start)
            mViewModel!!.end.setValue(end)
            null
        }
        return mBinding!!.getRoot()
    }

    fun doSave() {
        mCallback?.onConfirm(mViewModel!!.start.value, mViewModel!!.end.value)
        dismiss()
    }

    fun doClear() {
        mCallback?.onConfirm(null, null)
        dismiss()
    }

    interface Callback {
        fun onConfirm(startTime: LocalTime?, endTime: LocalTime?)
    }

}