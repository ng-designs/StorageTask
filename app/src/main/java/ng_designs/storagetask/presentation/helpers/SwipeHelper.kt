package ng_designs.storagetask.presentation.helpers

import androidx.recyclerview.widget.ItemTouchHelper
import ng_designs.storagetask.domain.entities.Order

class SwipeHelper(onSwiped: (Order) -> Unit): ItemTouchHelper(SwipeCallback(onSwiped))