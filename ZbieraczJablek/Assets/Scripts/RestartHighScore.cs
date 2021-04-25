using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RestartHighScore : MonoBehaviour
{
    private GameObject textField;

    void Start()
    {
        textField = GameObject.Find("GameInfo");
        textField.SetActive(false);
    }
    public void OnButtonPress()
    {
        if (PlayerPrefs.HasKey("HighScore"))
        {
            PlayerPrefs.SetInt("HighScore", 0);
            textField.SetActive(true);
        }
    }
}
