package com.example.lazycolumnwithstickyheaders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazycolumnwithstickyheaders.ui.theme.LazyColumnWithStickyHeadersTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumnWithStickyHeadersTheme {
                ContactListScreen()
            }
        }
    }
}

data class Contact(val name: String, val title: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen() {
    val contacts = remember { sampleContacts().sortedBy { it.name } }
    val grouped = remember(contacts) {
        contacts.groupBy { it.name.first().uppercaseChar() }.toSortedMap()
    }

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 10 }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Main list
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp)
        ) {
            grouped.forEach { (letter, people) ->
                stickyHeader { Header(letter) }
                items(people, key = { it.name }) { person ->
                    ContactRow(person)
                }
            }
        }

        // Floating TOP button in the top-right
        if (showFab) {
            Button(
                onClick = { scope.launch { listState.animateScrollToItem(0) } },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Text("TOP")
            }
        }
    }
}


@Composable
private fun Header(letter: Char) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = letter.toString(),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ContactRow(contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Simple circle avatar placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small),
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(contact.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(contact.title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
    Divider()
}

/* ---------- Sample Data (50+ modern US political figures) ---------- */
private fun sampleContacts(): List<Contact> = listOf(
    Contact("Adam Schiff", "U.S. Senator (CA)"),
    Contact("Alexandria Ocasio-Cortez", "U.S. Representative (NY)"),
    Contact("Amy Klobuchar", "U.S. Senator (MN)"),
    Contact("Andy Beshear", "Governor of Kentucky"),
    Contact("Ayanna Pressley", "U.S. Representative (MA)"),
    Contact("Bernie Sanders", "U.S. Senator (VT)"),
    Contact("Beto O'Rourke", "Former U.S. Representative (TX)"),
    Contact("Kari Lake", "Political figure (AZ)"),
    Contact("Brian Kemp", "Governor of Georgia"),
    Contact("Chuck Schumer", "U.S. Senate Majority Leader"),
    Contact("Dan Crenshaw", "U.S. Representative (TX)"),
    Contact("Donald Trump", "President of the United States"),
    Contact("Elise Stefanik", "U.S. Representative (NY)"),
    Contact("Gavin Newsom", "Governor of California"),
    Contact("Gretchen Whitmer", "Governor of Michigan"),
    Contact("Hakeem Jeffries", "U.S. House Minority Leader"),
    Contact("Ilhan Omar", "U.S. Representative (MN)"),
    Contact("J.B. Pritzker", "Governor of Illinois"),
    Contact("J.D. Vance", "Vice President of the United States"),
    Contact("Zohran Mamdani", "NYC Mayoral Candidate"),
    Contact("Jared Polis", "Governor of Colorado"),
    Contact("Jared Golden", "U.S. Representative (ME)"),
    Contact("Jim Jordan", "U.S. Representative (OH)"),
    Contact("Joe Biden", "Former President"),
    Contact("Jim Justice", "U.S. Senator (WV)"),
    Contact("John Fetterman", "U.S. Senator (PA)"),
    Contact("Josh Hawley", "U.S. Senator (MO)"),
    Contact("Josh Shapiro", "Governor of Pennsylvania"),
    Contact("Katie Hobbs", "Governor of Arizona"),
    Contact("Dave Min", "U.S. Representative (CA)"),
    Contact("Kathy Hochul", "Governor of New York"),
    Contact("Kevin Kiley", "U.S. Representative (CA)"),
    Contact("Kim Reynolds", "Governor of Iowa"),
    Contact("Jeffery Epstein", "Best Friend of Donald Trump"),
    Contact("Lauren Boebert", "U.S. Representative (CO)"),
    Contact("Maggie Hassan", "U.S. Senator (NH)"),
    Contact("Marjorie Taylor Greene", "U.S. Representative (GA)"),
    Contact("Pete Hegseth", "U.S. Secretary of Defense"),
    Contact("Pam Bondi", "U.S. Attorney General"),
    Contact("Mike Johnson", "Speaker of the U.S. House"),
    Contact("Mitch McConnell", "U.S. Senator (KY)"),
    Contact("Muriel Bowser", "Mayor of Washington, D.C."),
    Contact("Nancy Pelosi", "U.S. Representative (CA)"),
    Contact("Nikki Haley", "Former U.N. Ambassador"),
    Contact("Pete Buttigieg", "Former U.S. Secretary of Transportation"),
    Contact("Phil Murphy", "Governor of New Jersey"),
    Contact("Pramila Jayapal", "U.S. Representative (WA)"),
    Contact("Rand Paul", "U.S. Senator (KY)"),
    Contact("Ritchie Torres", "U.S. Representative (NY)"),
    Contact("Ro Khanna", "U.S. Representative (CA)"),
    Contact("Ron DeSantis", "Governor of Florida"),
    Contact("Sarah Huckabee Sanders", "Governor of Arkansas"),
    Contact("Ted Cruz", "U.S. Senator (TX)"),
    Contact("Tim Scott", "U.S. Senator (SC)"),
    Contact("Tom Cotton", "U.S. Senator (AR)"),
    Contact("Vivek Ramaswamy", "Gubernatorial Candidate (OH)"),
    Contact("Wes Moore", "Governor of Maryland")
)