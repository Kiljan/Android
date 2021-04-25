using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HighScore : MonoBehaviour
{
    static public int score = 1000;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    void Awake()
    {
        // je�eli istnieje ju� warto�� HighScore zapisana w zmiennych PlayerPrefs, odczytaj j�
        if (PlayerPrefs.HasKey("HighScore"))
        {
            score = PlayerPrefs.GetInt("HighScore");
        }
        // przypisz warto�� najlepszego wyniku do HighScore
        PlayerPrefs.SetInt("HighScore", score);
    }

    // Update is called once per frame
    void Update()
    {
        Text gt = this.GetComponent<Text>();
        gt.text = "Najlepszy wynik: " + score;

        // w razie konieczno�ci zaktualizuj warto�� HighScore zapami�tan� w zmiennych PlyerPrefs
        if (score > PlayerPrefs.GetInt("HighScore"))
        {
            PlayerPrefs.SetInt("HighScore", score);
        }
    }

}
