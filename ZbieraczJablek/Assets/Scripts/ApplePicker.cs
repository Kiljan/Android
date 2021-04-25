using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ApplePicker : MonoBehaviour
{
    [Header("DEfiniowane w polu inspekcyjnym")]
    public GameObject basketPrefab;
    public int numBascets = 3;
    public float bascetBottomY = -14f;
    public float baasketSpacingY = 2f;
    public List<GameObject> bascetList;

    // Start is called before the first frame update
    void Start()
    {
        bascetList = new List<GameObject>();
        for (int i = 0; i < numBascets; i++)
        {
            GameObject tBascetGO = Instantiate<GameObject>(basketPrefab);
            Vector3 pos = Vector3.zero;
            pos.y = bascetBottomY + (baasketSpacingY * i);
            tBascetGO.transform.position = pos;
            bascetList.Add(tBascetGO);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void AppleDestroyed()
    {
        // usu� wszystkie spdaj�ce jab�ka
        GameObject[] tAppleArray = GameObject.FindGameObjectsWithTag("Apple");
        foreach( GameObject tGO in tAppleArray)
        {
            Destroy(tGO);
        }

        // usu� jeden z koszy
        // pobie� indeks ostatniego elementu Bascet na li�cie bascetList
        int bascetIndex = bascetList.Count - 1;
        // pobierz referencj� do obiektu Bascet klasy GameObject 
        GameObject tBascetGO = bascetList[bascetIndex];
        // usu� element Bascet z listy i zniszcz obiekt gry
        bascetList.RemoveAt(bascetIndex);
        Destroy(tBascetGO);

        // je�li nie ma ju� koszy zresetuj gr�
        if (bascetList.Count == 0)
        {
            SceneManager.LoadScene("EndScene");
        }
    }
}
