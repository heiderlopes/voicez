package br.com.heiderlopes.voicez.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import br.com.heiderlopes.voicez.R;
import br.com.heiderlopes.voicez.adapter.NameListAdapter;
import br.com.heiderlopes.voicez.controller.PeopleController;
import br.com.heiderlopes.voicez.controller.PhraseController;
import br.com.heiderlopes.voicez.model.People;
import br.com.heiderlopes.voicez.model.Phrase;

public class NameList extends ActionBarActivity implements TextToSpeech.OnInitListener {

    private final String TAG = "VOICE Z";

    private TextToSpeech tts;
    private ListView lstName;
    private List<People> alPeople;
    private List<Phrase> alPhrases;

    private PeopleController peopleController;
    private PhraseController phraseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        peopleController = new PeopleController(this);
        phraseController = new PhraseController(this);
        setContentView(R.layout.activity_name_list);
        tts = new TextToSpeech(this, this);
        lstName = (ListView) findViewById(R.id.lstNames);
        registerForContextMenu(lstName);

        loadListViewName();
    }

    private void loadListViewName() {
        alPeople = peopleController.findAllInOrderByNome(true);
        lstName.setAdapter(new NameListAdapter(this, alPeople));

        lstName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showList(alPeople.get(position).getName());
            }
        });

        lstName.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int pos, long id) {
                confirmDelete(pos);
                return true;
            }
        });
    }

    private void confirmDelete(final int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NameList.this);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.msg_delete));

        builder.setPositiveButton(getResources().getString(R.string.label_btn_yes), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                People people = alPeople.get(id);
                try {
                    if(peopleController.delete(people) == 1 ) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_delete_success), Toast.LENGTH_SHORT).show();
                        loadListViewName();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_delete_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException sql) {
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.label_btn_no), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void addName() {
        View view = getLayoutInflater().inflate(R.layout.input_dialog_simple, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final EditText etValue = (EditText) view.findViewById(R.id.etValue);

        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.title_what_name_add));

        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.label_btn_ok), new Dialog.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               People people = new People(0, etValue.getText().toString());
               try {
                   if(peopleController.save(people) == 1 ) {
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_insert_success), Toast.LENGTH_SHORT).show();
                       loadListViewName();
                   } else {
                       Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_insert_error), Toast.LENGTH_SHORT).show();
                   }
               } catch (SQLException sql) {

               }
           }
        });

        builder.setNegativeButton(getResources().getString(R.string.label_btn_cancel), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void showList(String name) {
        final String nameSelected = name;
        alPhrases = phraseController.findAllInOrderByPhrase(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_select_phrase));

        builder.setAdapter(new ArrayAdapter<Phrase>(NameList.this, R.layout.list_item_simple, alPhrases), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                speakOut(nameSelected + alPhrases.get(position));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.name_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_more_phrase:
                addPhrase();
                break;
            case R.id.action_add_name:
                addName();
                break;

            case R.id.action_custom_phrase:
                customPhrase();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, getResources().getString(R.string.msg_error_language_not_supported));
            } else {
                Log.e(TAG, getResources().getString(R.string.msg_tts_initialize_success));
            }
        } else {
            Log.e(TAG, getResources().getString(R.string.msg_tts_initialize_failed));
        }

    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    private void customPhrase() {
        View view = getLayoutInflater().inflate(R.layout.input_dialog_simple, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final EditText etValue = (EditText) view.findViewById(R.id.etValue);

        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.label_input_phrase));

        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.label_btn_ok), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                speakOut(etValue.getText().toString());
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.label_btn_cancel), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void addPhrase() {
        View view = getLayoutInflater().inflate(R.layout.input_dialog_simple, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final EditText etValue = (EditText) view.findViewById(R.id.etValue);

        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.title_add_phrase));

        builder.setCancelable(false);
        builder.setPositiveButton("OK", new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Phrase phrase = new Phrase(0, etValue.getText().toString());
                try {
                    if(phraseController.save(phrase) == 1 ) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_insert_success), Toast.LENGTH_SHORT).show();
                        loadListViewName();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.msg_insert_error), Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException sql) {

                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.label_btn_cancel), new Dialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
