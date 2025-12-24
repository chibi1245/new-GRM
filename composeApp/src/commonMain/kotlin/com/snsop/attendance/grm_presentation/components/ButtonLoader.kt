

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

import com.snsop.attendance.ui.theme.AttendanceTheme
import com.snsop.attendance.ui.theme.primary

@Composable
fun ButtonLoader(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = 3.dp,
        color = primary
    )
}

@Preview(showBackground = true)
@Composable
private fun ButtonLoaderPreview() {
    AttendanceTheme {
        ButtonLoader(
            modifier = Modifier.size(24.dp)
        )
    }
}
