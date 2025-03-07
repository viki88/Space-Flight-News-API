package com.vikination.spaceflightnewsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vikination.spaceflightnewsapp.ui.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    type :String,
    searchQuery : String,
    navController: NavController,
    onSearchClicked:() -> Unit,
    onSearchQueryChanged : (String) -> Unit,
    onFilterSelected : (String) -> Unit,
    onSortSelected : (String) -> Unit,
    onSelectedRecentSearch : (String) -> Unit,
    listMenuFilter :List<String>,
    recentSearches :List<String>
) {

    var isSearching by remember { mutableStateOf(false) }
    var showMenuFilter by remember { mutableStateOf(false) }
    var showMenuSort by remember { mutableStateOf(false) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            if(isSearching){
                ExposedDropdownMenuBox(expanded = isDropdownExpanded, onExpandedChange = {
                    isDropdownExpanded = it
                }){
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester = focusRequester)
                            .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                        value = searchQuery,
                        onValueChange = {
                            onSearchQueryChanged(it)
                            isDropdownExpanded = it.isNotEmpty()
                        },
                        placeholder = { Text("Search...") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearchClicked()
                                isDropdownExpanded = false
                                focusManager.clearFocus()
                            }
                        )
                    )
                    ExposedDropdownMenu (
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.fillMaxWidth().background(Color.White).padding(horizontal = 16.dp)
                    ) {
                        recentSearches.forEach { query ->
                            DropdownMenuItem(
                                text = { Text(query) },
                                onClick = {
                                    onSelectedRecentSearch(query)
                                    focusRequester.requestFocus()
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }else Text(type)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
        },
        actions = {
            if(isSearching) {
                IconButton(onClick = { isSearching = false }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Search"
                    )
                }
            }else{
                IconButton(onClick = { isSearching = true }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
                Box {
                    IconButton(onClick = { showMenuFilter = true }) {
                        Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filter")
                    }
                    DropdownMenu(
                        expanded = showMenuFilter,
                        onDismissRequest = { showMenuFilter = false },
                        modifier = Modifier.requiredSizeIn(maxHeight = 300.dp)
                    ) {
                        for (filter in listMenuFilter){
                            DropdownMenuItem(text = { Text(filter) }, onClick = {
                                onFilterSelected(filter)
                                showMenuFilter = false
                            })
                        }
                    }
                }

                // Sort Menu
                Box {
                    IconButton(onClick = { showMenuSort = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Sort,
                            contentDescription = "Sort"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenuSort,
                        onDismissRequest = { showMenuSort = false }
                    ) {
                        DropdownMenuItem(text = { Text(Constants.ASC) }, onClick = {
                            onSortSelected(Constants.ASC)
                            showMenuSort = false
                        })
                        DropdownMenuItem(text = { Text(Constants.DESC) }, onClick = {
                            onSortSelected(Constants.DESC)
                            showMenuSort = false
                        })
                    }
                }
            }
        }
    )
}