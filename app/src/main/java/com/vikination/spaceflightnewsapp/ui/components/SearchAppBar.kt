package com.vikination.spaceflightnewsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController

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
    listMenuFilter :List<String>,
) {

    var isSearching by remember { mutableStateOf(false) }
    var showMenuFilter by remember { mutableStateOf(false) }
    var showMenuSort by remember { mutableStateOf(false) }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            if(isSearching){
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChanged,
                    placeholder = { Text("Search...") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClicked()
                        }
                    )
                )
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
                        onDismissRequest = { showMenuFilter = false }
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
                        DropdownMenuItem(text = { Text("Asc") }, onClick = {
                            onSortSelected("Ascending")
                            showMenuSort = false
                        })
                        DropdownMenuItem(text = { Text("Desc") }, onClick = {
                            onSortSelected("Descending")
                            showMenuSort = false
                        })
                    }
                }
            }
        }
    )
}